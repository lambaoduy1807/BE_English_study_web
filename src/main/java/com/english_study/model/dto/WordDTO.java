package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordDTO {
    private String id;
    private String word;
    private String mean;
    private String type;
    private String example;
    private String vocabID;
}
