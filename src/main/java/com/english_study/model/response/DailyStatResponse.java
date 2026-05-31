package com.english_study.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DailyStatResponse {
    private String id;
    private LocalDate date;
    private LocalDateTime updatedAt;
    private int totalNumMemorizeNew;
    private int totalXpNew;
    private List<DailyVocabSetDetail> vocabSets;
}
