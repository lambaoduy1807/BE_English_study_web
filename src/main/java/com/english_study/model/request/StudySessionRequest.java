package com.english_study.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudySessionRequest {
    private String vocabId;
    private int newWords;
    private int xpGained;
    private int progress;
    private java.util.List<String> memoryWords;
    private java.util.List<String> clozeWords;
}
