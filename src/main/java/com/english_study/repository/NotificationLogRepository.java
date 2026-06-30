package com.english_study.repository;

import com.english_study.model.entity.NotificationLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationLogRepository extends MongoRepository<NotificationLogEntity, String> {
    List<NotificationLogEntity> findAllByOrderByCreatedAtDesc();
}
