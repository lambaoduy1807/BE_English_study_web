package com.english_study.service;

import io.github.thoroldvix.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranscriptService {
    private final YoutubeTranscriptApi youtubeTranscriptApi;
    public TranscriptService() {
        this.youtubeTranscriptApi = TranscriptApiFactory.createDefault();
    }
    public String getTranscriptContent(String videoId) {
        TranscriptContent content ;
            try {
                content = youtubeTranscriptApi.getTranscript(videoId);
            } catch (TranscriptRetrievalException e) {
                throw new RuntimeException(e);
            }
        return TranscriptFormatters.jsonFormatter().format(content);
    }
}
