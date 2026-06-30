package com.english_study.repository;

import com.english_study.model.entity.IconEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IconRepository extends MongoRepository<IconEntity, String> {
    List<IconEntity> findAllByOrderByCreatedAtDesc();
}
