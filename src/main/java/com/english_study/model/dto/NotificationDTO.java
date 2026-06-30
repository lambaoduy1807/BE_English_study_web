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
public class NotificationDTO {
    private String id;
    private String userId;
    private String title;
    private String message;
    @com.fasterxml.jackson.annotation.JsonProperty("read")
    private boolean isRead;
    private Date createdAt;
}
