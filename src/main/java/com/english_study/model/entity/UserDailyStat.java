package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "user_daily_stats")
public class UserDailyStat {
    @Id
    private String id;
    private String userId;
    private LocalDate date;
    private LocalDateTime updatedAt;
    private int numMemorizeNew;
    private int xpNew;
    private List<String> vocabSets;
}
