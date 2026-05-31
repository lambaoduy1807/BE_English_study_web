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

    public Page<DailyStatResponse> getRecentStudyHistory(String userId, Pageable pageable) {
        Page<UserDailyStat> stats = userDailyStatRepository.findByUserIdOrderByDateDesc(userId, pageable);

        return stats.map(stat -> {
            List<DailyVocabSetDetail> vocabDetails = new ArrayList<>();
            if (stat.getVocabSets() != null && !stat.getVocabSets().isEmpty()) {
                for (String vocabId : stat.getVocabSets()) {
                    String vocabName = vocabSetRepository.findById(vocabId)
                            .map(VocabSetEntity::getName)
                            .orElse("Unknown");

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

        List<UserDailyStat> stats = userDailyStatRepository.findByUserIdAndDateBetweenOrderByDateAsc(userId, startDate, endDate);

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
}
