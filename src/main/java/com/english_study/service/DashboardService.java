package com.english_study.service;

import com.english_study.model.dto.DashboardResponseDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.model.entity.UserVideoProgress;
import com.english_study.repository.UserRepository;
import com.english_study.repository.UserVideoProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final UserVideoProgressRepository progressRepository;

//    public DashboardResponseDTO getDashboardData(String userId) {
//        UserEntity user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String level = user.getLevel();
//        String dailyGoalStr = generateDailyGoalByLevel(level);
//        int dailyGoalVal = Integer.parseInt(dailyGoalStr.replace(" XP", ""));
//
//        int currentXp = 0;
////        if (user.getLastXpUpdateDate() != null && user.getTodayXP() != 0) {
////            java.time.LocalDate lastUpdate = new java.sql.Date(user.getLastXpUpdateDate().getTime()).toLocalDate();
////            java.time.LocalDate today = java.time.LocalDate.now();
////            if (lastUpdate.isEqual(today)) {
////                currentXp = user.getTodayXP();
////            }
////        }
//
//        int progressPercent = (int) (((double) currentXp / dailyGoalVal) * 100);
//        if (progressPercent > 100) progressPercent = 100;
//
//        DashboardResponseDTO response = DashboardResponseDTO.builder()
//                .dailyGoal(dailyGoalStr)
//                .todayXP(currentXp)
//                .progressPercent(progressPercent)
//                .checkinHistory(user.getCheckinHistory() != null ? user.getCheckinHistory() : new ArrayList<>())
//                .build();
//
//        Optional<UserVideoProgress> progressOpt = progressRepository.findTopByUserIdOrderByLastWatchedDesc(userId);
//        if (progressOpt.isPresent()) {
//            UserVideoProgress progress = progressOpt.get();
//            response.setCurrentVideo(DashboardResponseDTO.VideoProgressDTO.builder()
//                    .videoId(progress.getVideoId())
//                    .resumeAt(progress.getResumeAt())
//                    .build());
//        }
//
//        return response;
//    }

    private String generateDailyGoalByLevel(String level) {
        if (level == null || level.trim().isEmpty()) {
            return "50 XP";
        }
        
        switch (level.toUpperCase().trim()) {
            case "A1":
                return "50 XP";
            case "A2":
                return "100 XP";
            case "B1":
                return "150 XP";
            case "B2":
                return "200 XP";
            case "C1":
                return "250 XP";
            case "C2":
                return "300 XP";
            default:
                return "50 XP";
        }
    }
}
