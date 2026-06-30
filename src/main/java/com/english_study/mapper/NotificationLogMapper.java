package com.english_study.mapper;

import com.english_study.model.dto.NotificationLogDTO;
import com.english_study.model.entity.NotificationLogEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationLogMapper {

    public NotificationLogDTO toDTO(NotificationLogEntity entity) {
        if (entity == null) return null;
        return NotificationLogDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .targetType(entity.getTargetType())
                .targetUserId(entity.getTargetUserId())
                .createdBy(entity.getCreatedBy())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
