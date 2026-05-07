package com.english_study.mapper;

import com.english_study.model.dto.WordDTO;
import com.english_study.model.entity.Word;
import org.springframework.stereotype.Component;

@Component
public class WordMapper {

    public WordDTO toDTO(Word entity) {
        if (entity == null) return null;

        return WordDTO.builder()
                .id(entity.getId())
                .word(entity.getWord())
                .mean(entity.getMean())
                .type(entity.getType())
                .example(entity.getExample())
                .vocabID(entity.getVocabID())
                .build();
    }

    public Word toEntity(WordDTO dto) {
        if (dto == null) return null;

        return Word.builder()
                .id(dto.getId())
                .word(dto.getWord())
                .mean(dto.getMean())
                .type(dto.getType())
                .example(dto.getExample())
                .vocabID(dto.getVocabID())
                .build();
    }
}
