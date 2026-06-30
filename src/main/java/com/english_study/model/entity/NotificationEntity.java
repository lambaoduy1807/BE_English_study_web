package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationEntity {
    @Id
    private String id;
    private String userId;
    private String title;
    private String message;
    private boolean isRead = false;
    private Date createdAt = new Date();
}
