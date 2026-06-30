package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConflictInfo {
    private WordDTO existingWord;
    private WordDTO newWord;
}
