package com.english_study.repository;

import com.english_study.model.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryEntity, Integer> {
}
