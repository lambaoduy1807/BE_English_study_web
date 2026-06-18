package com.english_study.mapper;

import com.english_study.model.dto.VideoDTO;
import com.english_study.model.entity.Video;
import org.springframework.stereotype.Component;

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
                .build();
    }
}
