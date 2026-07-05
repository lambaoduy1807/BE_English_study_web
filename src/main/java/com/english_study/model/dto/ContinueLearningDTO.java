package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContinueLearningDTO {
    private String type; // "VIDEO" or "VOCAB"
    private String id;
    private String title;
    private String description;
    private String imageSrc;
    private String duration; // e.g., "02:45" or "20 từ"
    private int progress; // 0-100 percentage
    private String level;
}
