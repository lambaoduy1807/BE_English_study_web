package com.english_study.config.dbmigrations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.english_study.model.entity.UserDailyStat;
import com.english_study.model.entity.UserStreak;

import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

@ChangeUnit(id = "add-streak-daily-stat-mock-data", order = "004", author = "Duy")
public class V2__AddStreakAndDailyStatMockData {

    @Execution
    public void execution(MongoTemplate mongoTemplate) {
        // 1. Mock UserStreak cho user u1
        UserStreak userStreak = UserStreak.builder()
                .userId("6a0d83c7b3bf7b68deb02c30")
                .currentStreak(5)
                .longestStreak(12)
                .lastActiveDay(LocalDate.now())
                .updatedAt(LocalDateTime.now())
                .numMemorize(15)
                .build();
        mongoTemplate.insert(userStreak);

        // 2. Mock UserDailyStat cho user u1 (dữ liệu cho 3 ngày gần nhất để test lấy theo tuần)
        LocalDate today = LocalDate.now();
        
        UserDailyStat statToday = UserDailyStat.builder()
                .userId("6a0d83c7b3bf7b68deb02c30")
                .date(today)
                .updatedAt(LocalDateTime.now())
                .numMemorizeNew(15)
                .xpNew(150)
                .vocabSets(Arrays.asList("vs1", "vs2"))
                .build();

        UserDailyStat statYesterday = UserDailyStat.builder()
                .userId("6a0d83c7b3bf7b68deb02c30")
                .date(today.minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .numMemorizeNew(10)
                .xpNew(100)
                .vocabSets(Arrays.asList("vs1"))
                .build();

        UserDailyStat stat2DaysAgo = UserDailyStat.builder()
                .userId("6a0d83c7b3bf7b68deb02c30")
                .date(today.minusDays(2))
                .updatedAt(LocalDateTime.now().minusDays(2))
                .numMemorizeNew(20)
                .xpNew(200)
                .vocabSets(Arrays.asList("vs2"))
                .build();

        mongoTemplate.insertAll(Arrays.asList(statToday, statYesterday, stat2DaysAgo));
    }

    @RollbackExecution
    public void rollbackExecution(MongoTemplate mongoTemplate) {
        mongoTemplate.remove(new Query(Criteria.where("userId").is("6a0d83c7b3bf7b68deb02c30")), UserStreak.class);
        mongoTemplate.remove(new Query(Criteria.where("userId").is("6a0d83c7b3bf7b68deb02c30")), UserDailyStat.class);
    }
}
