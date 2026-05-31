package com.english_study.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatsResponse {
    private int totalLearnedWords;
    private int currentStreak;
    private int longestStreak;
    private int totalWordToday;
    private int totalXp;
    private String level;
    private String rank;
}
