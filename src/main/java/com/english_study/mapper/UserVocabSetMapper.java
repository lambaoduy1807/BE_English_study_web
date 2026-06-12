package com.english_study.mapper;

import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.model.entity.UserVocabSet;
import org.springframework.stereotype.Component;

@Component
public class UserVocabSetMapper {

    public UserVocabSetDTO toDTO(UserVocabSet entity) {
        if (entity == null) return null;

        return UserVocabSetDTO.builder()
                .id(entity.getId())
                .userID(entity.getUserID())
                .vocabID(entity.getVocabID())
                .learningProgress(entity.getLearningProgress())
                .memoryWords(entity.getMemoryWords())
                .clozeWords(entity.getClozeWords())
                .build();
    }

    public UserVocabSet toEntity(UserVocabSetDTO dto) {
        if (dto == null) return null;

        return UserVocabSet.builder()
                .id(dto.getId())
                .userID(dto.getUserID())
                .vocabID(dto.getVocabID())
                .learningProgress(dto.getLearningProgress())
                .memoryWords(dto.getMemoryWords())
                .clozeWords(dto.getClozeWords())
                .build();
    }
}
