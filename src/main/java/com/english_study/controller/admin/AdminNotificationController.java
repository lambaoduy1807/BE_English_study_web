package com.english_study.controller.admin;

import com.english_study.model.entity.NotificationLogEntity;
import com.english_study.model.response.ApiResponse;
import com.english_study.service.NotificationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
public class AdminNotificationController {

    private final NotificationService notificationService;

    @GetMapping("/history")
    public ApiResponse<List<NotificationLogEntity>> getHistory() {
        return ApiResponse.success(notificationService.getNotificationHistory(), "Lấy lịch sử thông báo thành công");
    }

    @PostMapping("/send")
    public ApiResponse<Void> sendNotification(@RequestBody SendNotificationRequest request) {
        notificationService.sendBulkNotification(
                request.getTargetType(),
                request.getTargetUserId(),
                request.getTitle(),
                request.getMessage()
        );
        return ApiResponse.success(null, "Gửi thông báo thành công");
    }

    @Data
    public static class SendNotificationRequest {
        private String targetType;
        private String targetUserId;
        private String title;
        private String message;
    }
}
