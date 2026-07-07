package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkNotificationEvent implements Serializable {
    private String targetType;
    private String targetUserId;
    private String title;
    private String message;
    private String createdBy;
}
