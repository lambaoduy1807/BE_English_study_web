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
                .title(entity.getTitle())
                .numOfWords(entity.getNumOfWords())
                .icon(entity.getIcon())
                .iconWrapClass(entity.getIconWrapClass())
                .barClass(entity.getBarClass())
                .build();
    }

    public VocabSetEntity toEntity(VocabSetDTO dto) {
        if (dto == null) return null;

        return VocabSetEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .numOfWords(dto.getNumOfWords())
                .icon(dto.getIcon())
                .iconWrapClass(dto.getIconWrapClass())
                .barClass(dto.getBarClass())
                .build();
    }
}
