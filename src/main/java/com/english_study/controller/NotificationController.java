package com.english_study.controller;

import com.english_study.mapper.NotificationMapper;
import com.english_study.model.dto.NotificationDTO;
import com.english_study.model.response.ApiResponse;
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
    public ApiResponse<List<NotificationDTO>> getNotifications() {
        String userId = SecurityUtil.getCurrentUserId();
        List<NotificationDTO> dtos = notificationService.getNotificationsByUserId(userId).stream().map(notificationMapper::toDTO).toList();
        return ApiResponse.success(dtos, "Lấy danh sách thông báo thành công");
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
