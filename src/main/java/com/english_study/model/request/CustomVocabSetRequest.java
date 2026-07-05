package com.english_study.model.request;

import com.english_study.model.dto.VocabSetDTO;
import com.english_study.model.dto.WordDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomVocabSetRequest {
    private VocabSetDTO vocabSet;
    private List<WordDTO> words;
}
