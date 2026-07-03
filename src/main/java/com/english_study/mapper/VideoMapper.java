package com.english_study.mapper;

import com.english_study.model.dto.VideoDTO;
import com.english_study.model.entity.Video;
import com.english_study.model.entity.TranscriptItem;
import com.english_study.model.dto.TranscriptItemDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VideoMapper {

    public VideoDTO toDTO(Video entity) {
        if (entity == null) return null;

        return VideoDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .url(entity.getUrl())
                .upload_date(entity.getUpload_date())
                .viewCount(entity.getViewCount())
                .duration(entity.getDuration())
                .transcripts(entity.getTranscripts() != null ? entity.getTranscripts().stream()
                        .map(t -> TranscriptItemDTO.builder()
                                .text(t.getText())
                                .start(t.getStart())
                                .dur(t.getDur())
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public Video toEntity(VideoDTO dto) {
        if (dto == null) return null;

        return Video.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .url(dto.getUrl())
                .upload_date(dto.getUpload_date())
                .viewCount(dto.getViewCount())
                .duration(dto.getDuration())
                .transcripts(dto.getTranscripts() != null ? dto.getTranscripts().stream()
                        .map(t -> TranscriptItem.builder()
                                .text(t.getText())
                                .start(t.getStart())
                                .dur(t.getDur())
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
