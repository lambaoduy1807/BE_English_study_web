package com.english_study.service;

import com.english_study.model.dto.DashboardResponseDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.model.entity.UserVideoProgress;
import com.english_study.repository.UserRepository;
import com.english_study.repository.UserVideoProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final UserVideoProgressRepository progressRepository;

    private final com.english_study.repository.UserDailyStatRepository userDailyStatRepository;

    public DashboardResponseDTO getDashboardData(String userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String level = user.getLevel();
        String dailyGoalStr = generateDailyGoalByLevel(level);
        int dailyGoalVal = Integer.parseInt(dailyGoalStr.replace(" XP", ""));

        // Get today XP from UserDailyStat
        int currentXp = userDailyStatRepository
                .findByUserIdAndDate(userId, java.time.LocalDate.now())
                .map(stat -> stat.getXpNew())
                .orElse(0);

        int progressPercent = (int) (((double) currentXp / dailyGoalVal) * 100);
        if (progressPercent > 100) progressPercent = 100;

        // Construct check-in history of dates
        java.time.LocalDate startDate = java.time.LocalDate.now().minusDays(30);
        java.time.LocalDate endDate = java.time.LocalDate.now();
        java.util.List<java.util.Date> checkinHistory = userDailyStatRepository
                .findByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate)
                .stream()
                .filter(stat -> stat.getXpNew() > 0 || stat.getNumMemorizeNew() > 0)
                .map(stat -> java.util.Date.from(stat.getDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()))
                .collect(java.util.stream.Collectors.toList());

        List<UserEntity> allUsers = userRepository.findAll();
        List<UserEntity> sortedUsers = new java.util.ArrayList<>(allUsers);
        sortedUsers.sort((u1, u2) -> Integer.compare(u2.getTotalXP(), u1.getTotalXP()));

        java.util.List<DashboardResponseDTO.LeaderboardUserDTO> leaderboard = new java.util.ArrayList<>();
        int topN = Math.min(10, sortedUsers.size());
        for (int i = 0; i < topN; i++) {
            UserEntity u = sortedUsers.get(i);
            String rankName = "Hạng " + (i + 1);
            if (i == 0) rankName = "Master";
            else if (i == 1) rankName = "Diamond";
            else if (i == 2) rankName = "Platinum";

            leaderboard.add(DashboardResponseDTO.LeaderboardUserDTO.builder()
                    .id(u.getId())
                    .name(u.getFullName() != null ? u.getFullName() : u.getUsername())
                    .xp(u.getTotalXP())
                    .rankPosition(i + 1)
                    .rankName(rankName)
                    .isCurrentUser(u.getId().equals(userId))
                    .build());
        }

        DashboardResponseDTO response = DashboardResponseDTO.builder()
                .dailyGoal(dailyGoalStr)
                .todayXP(currentXp)
                .progressPercent(progressPercent)
                .checkinHistory(checkinHistory)
                .leaderboard(leaderboard)
                .build();

        Optional<UserVideoProgress> progressOpt = progressRepository.findTopByUserIdOrderByLastWatchedDesc(userId);
        if (progressOpt.isPresent()) {
            UserVideoProgress progress = progressOpt.get();
            response.setCurrentVideo(DashboardResponseDTO.VideoProgressDTO.builder()
                    .videoId(progress.getVideoId())
                    .resumeAt(progress.getResumeAt())
                    .build());
        }

        return response;
    }

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
