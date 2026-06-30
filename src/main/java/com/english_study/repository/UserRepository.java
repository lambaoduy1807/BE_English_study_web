package com.english_study.repository;

import com.english_study.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);

    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);

    UserEntity findByVerificationToken(String token);
    UserEntity findByResetPasswordToken(String token);
    @Query("{ '_id' : ?0 }")
    @Update("{ '$set' : { 'refreshToken' : ?1 } }")
    void updateRefreshtoken(String id, String refreshToken);
}
