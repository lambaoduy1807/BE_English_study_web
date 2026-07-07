package com.english_study.service;

import com.english_study.model.entity.UserDailyStat;
import com.english_study.model.entity.UserVocabSet;
import com.english_study.model.entity.VocabSetEntity;
import com.english_study.model.response.DailyStatResponse;
import com.english_study.model.response.DailyVocabSetDetail;
import com.english_study.model.response.WeeklyStatResponse;
import com.english_study.repository.UserDailyStatRepository;
import com.english_study.repository.UserVocabSetRepository;
import com.english_study.repository.VocabSetRepository;
import com.english_study.repository.VideoRepository;
import com.english_study.model.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDailyStatService {

    private final UserDailyStatRepository userDailyStatRepository;
    private final UserVocabSetRepository userVocabSetRepository;
    private final VocabSetRepository vocabSetRepository;
    private final VideoRepository videoRepository;

    public Page<DailyStatResponse> getRecentStudyHistory(String userId, Pageable pageable) {
        Page<UserDailyStat> stats = userDailyStatRepository.findByUserIdOrderByDateDesc(userId, pageable);

        return stats.map(stat -> {
            List<DailyVocabSetDetail> vocabDetails = new ArrayList<>();
            if (stat.getVocabSets() != null && !stat.getVocabSets().isEmpty()) {
                for (String vocabId : stat.getVocabSets()) {
                    String vocabName;
                    if (vocabId.startsWith("VID_")) {
                        String videoId = vocabId.substring(4);
                        vocabName = videoRepository.findById(videoId)
                                .map(Video::getTitle)
                                .orElse("Video bài tập");
                    } else {
                        vocabName = vocabSetRepository.findById(vocabId)
                                .map(VocabSetEntity::getTitle)
                                .orElse("Unknown");
                    }

                    UserVocabSet userVocabSet = userVocabSetRepository.findByUserIDAndVocabID(userId, vocabId);

                    int numMemNew = 0;
                    int xpNew = 0;
                    LocalDateTime updatedAt = null;

                    if (userVocabSet != null) {
                        numMemNew = userVocabSet.getNumMemorizeNew();
                        xpNew = userVocabSet.getXpNew();
                        updatedAt = userVocabSet.getUpdatedAt();
                    }

                    vocabDetails.add(DailyVocabSetDetail.builder()
                            .vocabId(vocabId)
                            .vocabName(vocabName)
                            .numMemorizeNew(numMemNew)
                            .xpNew(xpNew)
                            .updatedAt(updatedAt)
                            .build());
                }
            }

            return DailyStatResponse.builder()
                    .id(stat.getId())
                    .date(stat.getDate())
                    .updatedAt(stat.getUpdatedAt())
                    .totalNumMemorizeNew(stat.getNumMemorizeNew())
                    .totalXpNew(stat.getXpNew())
                    .vocabSets(vocabDetails)
                    .build();
        });
    }

    public List<WeeklyStatResponse> getWeeklyNewWords(String userId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(6); // 7 days including today

        List<UserDailyStat> stats = userDailyStatRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, startDate.minusDays(1), endDate.plusDays(1));

        // Map to quickly find stats by date
        Map<LocalDate, UserDailyStat> statMap = stats.stream()
                .collect(Collectors.toMap(UserDailyStat::getDate, Function.identity()));

        List<WeeklyStatResponse> weeklyStats = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            UserDailyStat stat = statMap.get(date);
            int newWords = (stat != null) ? stat.getNumMemorizeNew() : 0;

            weeklyStats.add(WeeklyStatResponse.builder()
                    .date(date)
                    .numMemorizeNew(newWords)
                    .build());
        }

        return weeklyStats;
    }

    public List<WeeklyStatResponse> getMonthlyNewWords(String userId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(29); // 30 days including today

        List<UserDailyStat> stats = userDailyStatRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, startDate.minusDays(1), endDate.plusDays(1));

        // Map to quickly find stats by date
        Map<LocalDate, UserDailyStat> statMap = stats.stream()
                .collect(Collectors.toMap(UserDailyStat::getDate, Function.identity()));

        List<WeeklyStatResponse> monthlyStats = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            LocalDate date = startDate.plusDays(i);
            UserDailyStat stat = statMap.get(date);
            int newWords = (stat != null) ? stat.getNumMemorizeNew() : 0;

            monthlyStats.add(WeeklyStatResponse.builder()
                    .date(date)
                    .numMemorizeNew(newWords)
                    .build());
        }

        return monthlyStats;
    }

    public void recordDailyStat(String userId, String vocabId, int newWords, int xpGained) {
        LocalDate today = LocalDate.now();
        UserDailyStat dailyStat = userDailyStatRepository.findByUserIdAndDate(userId, today)
                .orElse(UserDailyStat.builder()
                        .userId(userId)
                        .date(today)
                        .numMemorizeNew(0)
                        .xpNew(0)
                        .vocabSets(new ArrayList<>())
                        .build());

        dailyStat.setNumMemorizeNew(dailyStat.getNumMemorizeNew() + newWords);
        dailyStat.setXpNew(dailyStat.getXpNew() + xpGained);
        dailyStat.setUpdatedAt(LocalDateTime.now());

        if (dailyStat.getVocabSets() == null) {
            dailyStat.setVocabSets(new ArrayList<>());
        }
        if (!dailyStat.getVocabSets().contains(vocabId)) {
            dailyStat.getVocabSets().add(vocabId);
        }

        userDailyStatRepository.save(dailyStat);
    }

    public void recordVideoStat(String userId, String videoId, int newWords, int xpGained) {
        String prefixedId = "VID_" + videoId;
        LocalDate today = LocalDate.now();
        UserDailyStat dailyStat = userDailyStatRepository.findByUserIdAndDate(userId, today)
                .orElse(UserDailyStat.builder()
                        .userId(userId)
                        .date(today)
                        .numMemorizeNew(0)
                        .xpNew(0)
                        .vocabSets(new ArrayList<>())
                        .build());

        dailyStat.setNumMemorizeNew(dailyStat.getNumMemorizeNew() + newWords);
        dailyStat.setXpNew(dailyStat.getXpNew() + xpGained);
        dailyStat.setUpdatedAt(LocalDateTime.now());

        if (dailyStat.getVocabSets() == null) {
            dailyStat.setVocabSets(new ArrayList<>());
        }
        if (!dailyStat.getVocabSets().contains(prefixedId)) {
            dailyStat.getVocabSets().add(prefixedId);
        }

        userDailyStatRepository.save(dailyStat);

        UserVocabSet userVocabSet = userVocabSetRepository.findByUserIDAndVocabID(userId, prefixedId);
        if (userVocabSet == null) {
            userVocabSet = UserVocabSet.builder()
                    .userID(userId)
                    .vocabID(prefixedId)
                    .numMemorizeNew(newWords)
                    .xpNew(xpGained)
                    .updatedAt(LocalDateTime.now())
                    .build();
        } else {
            userVocabSet.setNumMemorizeNew(userVocabSet.getNumMemorizeNew() + newWords);
            userVocabSet.setXpNew(userVocabSet.getXpNew() + xpGained);
            userVocabSet.setUpdatedAt(LocalDateTime.now());
        }
        userVocabSetRepository.save(userVocabSet);
    }
}
