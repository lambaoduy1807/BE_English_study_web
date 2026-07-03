package com.english_study.controller;

import com.english_study.mapper.NotificationMapper;
import com.english_study.model.dto.NotificationDTO;
import org.springframework.http.ResponseEntity;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    @GetMapping("/get-all")
    public ResponseEntity<List<NotificationDTO>> getNotifications() {
        String userId = SecurityUtil.getCurrentUserId();
        List<NotificationDTO> dtos = notificationService.getNotificationsByUserId(userId).stream().map(notificationMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/read")
    public ResponseEntity<?> markAllAsRead() {
        String userId = SecurityUtil.getCurrentUserId();
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }
    
    // API ẩn dùng để test việc tạo thông báo
    @PostMapping("/test-create")
    public ResponseEntity<?> createTestNotification(@RequestParam String title, @RequestParam String message) {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(notificationService.createNotification(userId, title, message));
    }
}
