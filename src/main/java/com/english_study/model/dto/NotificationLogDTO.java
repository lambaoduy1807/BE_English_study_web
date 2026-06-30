package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLogDTO {
    private String id;
    private String title;
    private String message;
    private String targetType;
    private String targetUserId;
    private String createdBy;
    private Date createdAt;
}
