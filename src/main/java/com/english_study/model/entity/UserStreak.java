package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "user_streaks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserStreak {

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    private int currentStreak;

    private int longestStreak;

    private LocalDate lastActiveDay;

    @Field("update_at")
    private LocalDateTime updatedAt;

    private int numMemorize;
}
