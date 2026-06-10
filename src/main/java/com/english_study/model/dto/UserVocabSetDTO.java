package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVocabSetDTO {
    private String id;
    private String userID;
    private String vocabID;
    private int learningProgress;
    private List<String> memoryWords;
    private String title;
    private int numOfWords;
    private String icon;
}
