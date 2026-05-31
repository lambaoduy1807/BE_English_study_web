package com.english_study.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DailyVocabSetDetail {
    private String vocabId;
    private String vocabName;
    private int numMemorizeNew;
    private int xpNew;
    private LocalDateTime updatedAt;
}
