package com.english_study.service.impl;

import com.english_study.model.entity.NotificationEntity;
import com.english_study.model.entity.NotificationLogEntity;
import com.english_study.model.entity.UserEntity;
import com.english_study.repository.NotificationLogRepository;
import com.english_study.repository.NotificationRepository;
import com.english_study.repository.UserRepository;
import com.english_study.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationLogRepository notificationLogRepository;
    private final UserRepository userRepository;

    private final org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate;

    @Override
    public List<NotificationEntity> getNotificationsByUserId(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public void markAllAsRead(String userId) {
        List<NotificationEntity> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        for (NotificationEntity notification : unreadNotifications) {
            notification.setRead(true);
        }
        if (!unreadNotifications.isEmpty()) {
            notificationRepository.saveAll(unreadNotifications);
        }
    }

    @Override
    public NotificationEntity createNotification(String userId, String title, String message) {
        NotificationEntity notification = NotificationEntity.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .isRead(false)
                .createdAt(new Date())
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public void sendBulkNotification(String targetType, String targetUserId, String title, String message, String createdBy) {
        // Lưu vào log ngay lập tức để Admin thấy
        NotificationLogEntity log = NotificationLogEntity.builder()
                .title(title)
                .message(message)
                .targetType(targetType)
                .targetUserId(targetUserId)
                .createdBy(createdBy)
                .createdAt(new Date())
                .build();
        notificationLogRepository.save(log);

        // Đẩy event vào RabbitMQ
        com.english_study.model.dto.BulkNotificationEvent event = com.english_study.model.dto.BulkNotificationEvent.builder()
                .targetType(targetType)
                .targetUserId(targetUserId)
                .title(title)
                .message(message)
                .createdBy(createdBy)
                .build();
        rabbitTemplate.convertAndSend(
                com.english_study.config.RabbitMQConfig.NOTIFICATION_BULK_EXCHANGE,
                com.english_study.config.RabbitMQConfig.NOTIFICATION_BULK_ROUTING_KEY,
                event
        );
    }

    @Override
    public void processBulkNotification(com.english_study.model.dto.BulkNotificationEvent event) {
        // Lấy danh sách người nhận
        List<UserEntity> targetUsers = new ArrayList<>();
        
        switch (event.getTargetType()) {
            case "ALL":
                targetUsers = userRepository.findAll();
                break;
            case "ADMIN":
                targetUsers = userRepository.findByRoleId("ROLE_ADMIN");
                break;
            case "USER":
                targetUsers = userRepository.findByRoleId("ROLE_USER");
                break;
            case "SPECIFIC":
                if (event.getTargetUserId() != null && !event.getTargetUserId().isEmpty()) {
                    userRepository.findById(event.getTargetUserId()).ifPresent(targetUsers::add);
                }
                break;
        }

        // Tạo thông báo cho từng người
        List<NotificationEntity> notifications = new ArrayList<>();
        for (UserEntity user : targetUsers) {
            notifications.add(NotificationEntity.builder()
                    .userId(user.getId())
                    .title(event.getTitle())
                    .message(event.getMessage())
                    .isRead(false)
                    .createdAt(new Date())
                    .build());
        }
        
        if (!notifications.isEmpty()) {
            // Có thể tối ưu bằng cách chia batch để insert (VD: mỗi 1000 bản ghi 1 lần save)
            // nhưng spring data saveAll đã xử lý khá tốt với transaction
            notificationRepository.saveAll(notifications);
        }
    }

    @Override
    public List<NotificationLogEntity> getNotificationHistory() {
        return notificationLogRepository.findAllByOrderByCreatedAtDesc();
    }
}
