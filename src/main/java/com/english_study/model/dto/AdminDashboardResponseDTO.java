package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponseDTO {
    private long totalUsers;
    private long activeLearners;
    private long totalVocabulary;
    private long videoLessons;
    private List<Integer> userActivityTrend; // Chart data
    private Map<String, Long> userDemographics; // Chart data (e.g. Beginner -> 10, Intermediate -> 20)
    private List<TopUserDTO> topUsers;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopUserDTO {
        private String fullName;
        private int totalXP;
        private long rankPosition;
        private String rankName;
    }
}
