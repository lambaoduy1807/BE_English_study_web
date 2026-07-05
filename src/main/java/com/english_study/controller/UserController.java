package com.english_study.controller;

import com.english_study.config.cloudiary.CloudinaryService;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> update(@RequestBody UpdateProfileRequest updateRequest) {
        String userID=SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok( userService.updateUser(userID,updateRequest));
    }

    @PostMapping("/update-image")
    public ResponseEntity<?> updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        String userID = SecurityUtil.getCurrentUserId();
        
        uploadService.validateImage(image);
        
        String url = cloudinaryService.upload(image);
        userService.updateAvatar(userID, url);
        
        return ResponseEntity.ok(url);
    }

    @GetMapping("/get-stats")
    public ResponseEntity<?> getStats() {
        String userID = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userService.getUserStats(userID));
    }

    // -- User Streak APIs --
    @GetMapping("/get-streak")
    public ResponseEntity<?> getStreak() {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userStreakService.getUserStreak(userId));
    }

    @PostMapping("/increase-streak")
    public ResponseEntity<?> increaseStreak() {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userStreakService.increaseStreak(userId));
    }

    @PostMapping("/reset-streak")
    public ResponseEntity<?> resetStreak() {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userStreakService.resetCurrentStreak(userId));
    }

    @PostMapping("/record-study-day")
    public ResponseEntity<?> recordStudyDay(@RequestParam(defaultValue = "0") int newWordsMemorized) {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userStreakService.recordStudyDay(userId, newWordsMemorized));
    }

    // -- User Daily Stat APIs --
    @GetMapping("/get-history")
    public ResponseEntity<?> getRecentStudyHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        String userId = SecurityUtil.getCurrentUserId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
        
        return ResponseEntity.ok(userDailyStatService.getRecentStudyHistory(userId, pageable));
    }

    @GetMapping("/get-weekly-stats")
    public ResponseEntity<?> getWeeklyNewWords() {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userDailyStatService.getWeeklyNewWords(userId));
    }

    @GetMapping("/get-monthly-stats")
    public ResponseEntity<?> getMonthlyNewWords() {
        String userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(userDailyStatService.getMonthlyNewWords(userId));
    }
}
