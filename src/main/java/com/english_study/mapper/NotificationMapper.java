package com.english_study.mapper;

import com.english_study.model.dto.NotificationDTO;
import com.english_study.model.entity.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(NotificationEntity entity) {
        if (entity == null) return null;
        return NotificationDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .isRead(entity.isRead())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
