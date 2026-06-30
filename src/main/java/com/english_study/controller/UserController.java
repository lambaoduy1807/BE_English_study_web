package com.english_study.controller;

import com.english_study.config.cloudiary.CloudinaryService;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.UpdateProfileRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.UploadService;
import com.english_study.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


import com.english_study.service.UserStreakService;
import com.english_study.service.UserDailyStatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    UserService userService;
    UploadService uploadService;
    CloudinaryService cloudinaryService;
    UserStreakService userStreakService;
    UserDailyStatService userDailyStatService;

    @PutMapping("/update-profile")
    public ApiResponse update(@RequestBody UpdateProfileRequest updateRequest) {
        String userID=SecurityUtil.getCurrentUserId();
        return ApiResponse.success( userService.updateUser(userID,updateRequest),"Cập nhật thông tin thành công");
    }

    @PostMapping("/update-image")
    public ApiResponse updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        String userID = SecurityUtil.getCurrentUserId();
        
        uploadService.validateImage(image);
        
        String url = cloudinaryService.upload(image);
        userService.updateAvatar(userID, url);
        
        return ApiResponse.success(url, "Cập nhật ảnh đại diện thành công");
    }

    @GetMapping("/get-stats")
    public ApiResponse getStats() {
        String userID = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(userService.getUserStats(userID), "Lấy thông tin thống kê thành công");
    }

    // -- User Streak APIs --
    @GetMapping("/get-streak")
    public ApiResponse getStreak() {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(userStreakService.getUserStreak(userId), "Lấy thông tin streak thành công");
    }

    @PostMapping("/increase-streak")
    public ApiResponse increaseStreak() {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(userStreakService.increaseStreak(userId), "Tăng streak thành công");
    }

    @PostMapping("/reset-streak")
    public ApiResponse resetStreak() {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(userStreakService.resetCurrentStreak(userId), "Reset streak thành công");
    }

    @PostMapping("/record-study-day")
    public ApiResponse recordStudyDay(@RequestParam(defaultValue = "0") int newWordsMemorized) {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(userStreakService.recordStudyDay(userId, newWordsMemorized), "Ghi nhận ngày học thành công");
    }

    // -- User Daily Stat APIs --
    @GetMapping("/get-history")
    public ApiResponse getRecentStudyHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        String userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        
        return ApiResponse.success(
                userDailyStatService.getRecentStudyHistory(userId, pageable), 
                "Lấy lịch sử học tập thành công"
        );
    }

    @GetMapping("/get-weekly-stats")
    public ApiResponse getWeeklyNewWords() {
        String userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.success(
                userDailyStatService.getWeeklyNewWords(userId), 
                "Lấy số lượng từ vựng mới theo tuần thành công"
        );
    }
}
