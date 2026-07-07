package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyStatEvent implements Serializable {
    private String userId;
    private String vocabId; // Can be null if it's a video
    private String videoId; // Can be null if it's a vocab set
    private int newWords;
    private int xpGained;
}
