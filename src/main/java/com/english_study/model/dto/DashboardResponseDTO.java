package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {
    private String dailyGoal;
    private int todayXP;
    private int progressPercent;
    private List<Date> checkinHistory;
    private VideoProgressDTO currentVideo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VideoProgressDTO {
        private String videoId;
        private int resumeAt;
    }
}
