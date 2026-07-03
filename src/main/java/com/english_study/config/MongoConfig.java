package com.english_study.config;

import com.english_study.model.entity.TranscriptItem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new StringToTranscriptItemConverter()));
    }

    public static class StringToTranscriptItemConverter implements Converter<String, TranscriptItem> {
        @Override
        public TranscriptItem convert(String source) {
            String text = source != null ? source.trim() : "";
            double start = 0.0;
            double dur = 0.0;
            
            try {
                if (source.trim().startsWith("{") && source.trim().endsWith("}")) {
                    String inner = source.trim().substring(1, source.trim().length() - 1);
                    // Handle map toString() format e.g. text=Hello, start=7.0, dur=5.0
                    // But text might contain commas, so we need to be careful
                    // Format is usually: text=..., start=..., dur=...
                    int startIdx = inner.lastIndexOf(", start=");
                    int durIdx = inner.lastIndexOf(", dur=");
                    
                    if (startIdx != -1 && durIdx != -1) {
                        String textPart = inner.substring(0, startIdx);
                        if (textPart.startsWith("text=")) {
                            text = textPart.substring(5).trim();
                        }
                        String startPart = inner.substring(startIdx + 8, durIdx);
                        String durPart = inner.substring(durIdx + 6);
                        
                        start = Double.parseDouble(startPart);
                        dur = Double.parseDouble(durPart);
                    }
                }
            } catch (Exception e) {
                // Fallback to defaults
                text = source != null ? source.trim() : "";
                start = 0.0;
                dur = 0.0;
            }

            return TranscriptItem.builder()
                    .text(text)
                    .start(start)
                    .dur(dur)
                    .build();
        }
    }
}
