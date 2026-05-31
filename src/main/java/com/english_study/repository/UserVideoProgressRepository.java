package com.english_study.repository;

import com.english_study.model.entity.UserVideoProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserVideoProgressRepository extends MongoRepository<UserVideoProgress, String> {
    Optional<UserVideoProgress> findTopByUserIdOrderByLastWatchedDesc(String userId);
}
