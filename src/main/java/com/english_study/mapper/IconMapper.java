package com.english_study.mapper;

import com.english_study.model.dto.IconDTO;
import com.english_study.model.entity.IconEntity;
import org.springframework.stereotype.Component;

@Component
public class IconMapper {

    public IconDTO toDTO(IconEntity entity) {
        if (entity == null) return null;
        return IconDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }
}
