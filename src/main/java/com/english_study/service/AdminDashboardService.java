package com.english_study.service;

import com.english_study.model.dto.AdminDashboardResponseDTO;
import com.english_study.model.entity.UserDailyStat;
import com.english_study.model.entity.UserEntity;
import com.english_study.repository.UserDailyStatRepository;
import com.english_study.repository.UserRepository;
import com.english_study.repository.UserStreakRepository;
import com.english_study.repository.VideoRepository;
import com.english_study.repository.VocabSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final VocabSetRepository vocabSetRepository;
    private final VideoRepository videoRepository;
    private final UserStreakRepository userStreakRepository;
    private final UserDailyStatRepository userDailyStatRepository;

    public AdminDashboardResponseDTO getDashboardData() {
        // Basic KPIs
        long totalUsers = userRepository.count();
        long totalVocabulary = vocabSetRepository.count();
        long videoLessons = videoRepository.count();
        long activeLearners = userStreakRepository.countByCurrentStreakGreaterThan(0);

        List<UserEntity> allUsers = userRepository.findAll();

        // User Activity Trend (Last 7 days total learned words or active users)
        List<Integer> activityTrend = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);
        List<UserDailyStat> recentStats = userDailyStatRepository.findByDateBetween(sevenDaysAgo, today);

        // User Demographics mapping (Học trong tuần vs Chưa học)
        Map<String, Long> userDemographics = new HashMap<>();
        long activeUsersThisWeek = recentStats.stream().map(UserDailyStat::getUserId).distinct().count();
        userDemographics.put("Đã học", activeUsersThisWeek);
        userDemographics.put("Chưa học", totalUsers - activeUsersThisWeek);

        // Leaderboard (Top Users based on totalXP)
        List<AdminDashboardResponseDTO.TopUserDTO> topUsers = new ArrayList<>();
        List<UserEntity> sortedUsers = new ArrayList<>(allUsers);
        sortedUsers.sort((u1, u2) -> Integer.compare(u2.getTotalXP(), u1.getTotalXP())); // Descending
        
        int topN = Math.min(10, sortedUsers.size());
        for (int i = 0; i < topN; i++) {
            UserEntity user = sortedUsers.get(i);
            String dynamicRank = "Hạng " + (i + 1);
            if (i == 0) dynamicRank = "Vàng";
            else if (i == 1) dynamicRank = "Bạc";
            else if (i == 2) dynamicRank = "Đồng";
            
            topUsers.add(AdminDashboardResponseDTO.TopUserDTO.builder()
                    .fullName(user.getFullName() != null ? user.getFullName() : user.getUsername())
                    .totalXP(user.getTotalXP())
                    .rankPosition(i + 1)
                    .rankName(dynamicRank)
                    .build());
        }


        
        // Group by Date
        Map<LocalDate, Integer> statsByDate = recentStats.stream()
                .collect(Collectors.groupingBy(
                        UserDailyStat::getDate,
                        Collectors.summingInt(stat -> 1) // Count active users per day
                ));

        for (int i = 6; i >= 0; i--) {
            LocalDate d = today.minusDays(i);
            activityTrend.add(statsByDate.getOrDefault(d, 0));
        }

        return AdminDashboardResponseDTO.builder()
                .totalUsers(totalUsers)
                .activeLearners(activeLearners)
                .totalVocabulary(totalVocabulary)
                .videoLessons(videoLessons)
                .userDemographics(userDemographics)
                .userActivityTrend(activityTrend)
                .topUsers(topUsers)
                .build();
    }
}
