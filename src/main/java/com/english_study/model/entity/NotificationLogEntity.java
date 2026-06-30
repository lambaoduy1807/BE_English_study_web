package com.english_study.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "notification_logs")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationLogEntity {
    @Id
    private String id;
    
    private String title;
    private String message;
    private String targetType; // ALL, ADMIN, USER, SPECIFIC
    private String targetUserId;
    
    @Builder.Default
    private Date createdAt = new Date();
}
