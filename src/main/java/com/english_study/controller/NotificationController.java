package com.english_study.controller;

import com.english_study.model.response.ApiResponse;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping()
    public ApiResponse getNotifications() {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(notificationService.getNotificationsByUserId(userId), "Lấy danh sách thông báo thành công");
    }

    @PutMapping("/read")
    public ApiResponse markAllAsRead() {
        String userId = SecurityUtil.getCurrentUserId();
        notificationService.markAllAsRead(userId);
        return ApiResponse.success(null, "Đã đánh dấu tất cả là đã đọc");
    }
    
    // API ẩn dùng để test việc tạo thông báo
    @PostMapping("/test-create")
    public ApiResponse createTestNotification(@RequestParam String title, @RequestParam String message) {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(notificationService.createNotification(userId, title, message), "Tạo thông báo test thành công");
    }
}
