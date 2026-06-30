package com.english_study.controller.admin;

import com.english_study.mapper.NotificationLogMapper;
import com.english_study.model.dto.NotificationLogDTO;
import com.english_study.model.entity.NotificationLogEntity;
import com.english_study.model.response.ApiResponse;
import com.english_study.model.response.ApiResponse;
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
    public ApiResponse<List<NotificationLogDTO>> getHistory() {
        List<NotificationLogDTO> dtos = notificationService.getNotificationHistory().stream().map(notificationLogMapper::toDTO).toList();
        return ApiResponse.success(dtos, "Lấy lịch sử thông báo thành công");
    }

    @PostMapping("/send")
    public ApiResponse<Void> sendNotification(@RequestBody SendNotificationRequest request) {
        notificationService.sendBulkNotification(
                request.getTargetType(),
                request.getTargetUserId(),
                request.getTitle(),
                request.getMessage(),
                SecurityUtil.getCurrentUserId()
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
