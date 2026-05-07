package com.english_study.mapper;

import com.english_study.model.dto.VocabSetDTO;
import com.english_study.model.entity.VocabSetEntity;
import org.springframework.stereotype.Component;

@Component
public class VocabSetMapper {

    public VocabSetDTO toDTO(VocabSetEntity entity) {
        if (entity == null) return null;

        return VocabSetDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .numOfWords(entity.getNumOfWords())
                .build();
    }

    public VocabSetEntity toEntity(VocabSetDTO dto) {
        if (dto == null) return null;

        return VocabSetEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numOfWords(dto.getNumOfWords())
                .build();
    }
}
