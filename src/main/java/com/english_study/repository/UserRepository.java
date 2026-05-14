package com.english_study.repository;

import com.english_study.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByName(String userName);

    boolean existsByName(String name);
    // Cập nhật đúng trường refreshToken dựa trên _id
    @Query("{ '_id' : ?0 }")
    @Update("{ '$set' : { 'refreshToken' : ?1 } }")
    void updateRefreshtoken(String id, String refreshToken);
}
