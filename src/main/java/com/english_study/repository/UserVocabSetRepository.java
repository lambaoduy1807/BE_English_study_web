package com.english_study.repository;

import com.english_study.model.entity.UserVocabSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVocabSetRepository extends MongoRepository<UserVocabSet, String> {
}
