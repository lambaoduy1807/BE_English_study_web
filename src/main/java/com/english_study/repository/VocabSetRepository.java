package com.english_study.repository;

import com.english_study.model.entity.VocabSetEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabSetRepository extends MongoRepository<VocabSetEntity, String> {
    List<VocabSetEntity> findByCreatedByIsNull();
    List<VocabSetEntity> findByCreatedBy(String createdBy);
}
