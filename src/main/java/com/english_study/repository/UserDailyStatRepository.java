package com.english_study.repository;

import com.english_study.model.entity.UserDailyStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserDailyStatRepository extends MongoRepository<UserDailyStat, String> {
    Page<UserDailyStat> findByUserIdOrderByDateDesc(String userId, Pageable pageable);
    java.util.Optional<UserDailyStat> findByUserIdAndDate(String userId, LocalDate date);
    List<UserDailyStat> findByUserIdAndDateBetweenOrderByDateAsc(String userId, LocalDate startDate, LocalDate endDate);
    List<UserDailyStat> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
