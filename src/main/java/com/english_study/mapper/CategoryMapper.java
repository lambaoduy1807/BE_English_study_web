package com.english_study.mapper;

import com.english_study.model.dto.CategoryDTO;
import com.english_study.model.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(CategoryEntity entity) {
        if (entity == null) return null;

        return CategoryDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        return CategoryEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
