package com.english_study.service;

import com.english_study.model.entity.NotificationEntity;
import java.util.List;

public interface NotificationService {
    List<NotificationEntity> getNotificationsByUserId(String userId);
    void markAllAsRead(String userId);
    // Hàm utility để test
    NotificationEntity createNotification(String userId, String title, String message);
    
    // Gửi thông báo
    void sendBulkNotification(String targetType, String targetUserId, String title, String message);
    List<com.english_study.model.entity.NotificationLogEntity> getNotificationHistory();
}
