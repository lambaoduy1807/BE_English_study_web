package com.english_study.controller.admin;

import com.english_study.mapper.NotificationLogMapper;
import com.english_study.model.dto.NotificationLogDTO;
import com.english_study.model.entity.NotificationLogEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.NotificationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notification")
@RequiredArgsConstructor
public class AdminNotificationController {

    private final NotificationService notificationService;
    private final NotificationLogMapper notificationLogMapper;

    @GetMapping("/get-history")
    public ResponseEntity<List<NotificationLogDTO>> getHistory() {
        List<NotificationLogDTO> dtos = notificationService.getNotificationHistory().stream().map(notificationLogMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendNotification(@RequestBody SendNotificationRequest request) {
        notificationService.sendBulkNotification(
                request.getTargetType(),
                request.getTargetUserId(),
                request.getTitle(),
                request.getMessage(),
                SecurityUtil.getCurrentUserId()
        );
        return ResponseEntity.ok().build();
    }

    @Data
    public static class SendNotificationRequest {
        private String targetType;
        private String targetUserId;
        private String title;
        private String message;
    }
}
