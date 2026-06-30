package com.english_study.repository;

import com.english_study.model.entity.NotificationEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationEntity, String> {
    List<NotificationEntity> findByUserIdOrderByCreatedAtDesc(String userId);
    List<NotificationEntity> findByUserIdAndIsReadFalse(String userId);
}
