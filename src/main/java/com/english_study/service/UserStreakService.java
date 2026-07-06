package com.english_study.service;

import com.english_study.model.entity.UserStreak;
import com.english_study.repository.UserStreakRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserStreakService {

    private final UserStreakRepository userStreakRepository;

    /**
     * Lấy thông tin Streak của user. Nếu chưa có thì tạo mới (default 0).
     */
    public UserStreak getUserStreak(String userId) {
        return userStreakRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserStreak newStreak = UserStreak.builder()
                            .userId(userId)
                            .currentStreak(0)
                            .longestStreak(0)
                            .numMemorize(0)
                            .build();
                    return userStreakRepository.save(newStreak);
                });
    }

    /**
     * 1. Tăng current streak lên 1 và tự động đối chiếu cập nhật longest streak.
     */
    public UserStreak increaseStreak(String userId) {
        UserStreak userStreak = getUserStreak(userId);
        userStreak.setCurrentStreak(userStreak.getCurrentStreak() + 1);
        userStreak.setLastActiveDay(LocalDate.now());
        userStreak.setUpdatedAt(LocalDateTime.now());
        
        // Cập nhật chuỗi dài nhất nếu chuỗi hiện tại đã vượt qua
        checkAndUpdateLongestStreak(userStreak);
        
        return userStreakRepository.save(userStreak);
    }

    /**
     * 2. Reset current streak về 0.
     */
    public UserStreak resetCurrentStreak(String userId) {
        UserStreak userStreak = getUserStreak(userId);
        userStreak.setCurrentStreak(0);
        userStreak.setUpdatedAt(LocalDateTime.now());
        return userStreakRepository.save(userStreak);
    }

    /**
     * 3. Cập nhật longest streak (so sánh và cập nhật từ currentStreak hiện tại).
     */
    public UserStreak updateLongestStreak(String userId) {
        UserStreak userStreak = getUserStreak(userId);
        checkAndUpdateLongestStreak(userStreak);
        return userStreakRepository.save(userStreak);
    }
    
    private void checkAndUpdateLongestStreak(UserStreak userStreak) {
        if (userStreak.getCurrentStreak() > userStreak.getLongestStreak()) {
            userStreak.setLongestStreak(userStreak.getCurrentStreak());
            userStreak.setUpdatedAt(LocalDateTime.now());
        }
    }

    /**
     * [Nâng cao] Hàm nghiệp vụ thực tế: Gọi hàm này khi user hoàn thành bài học để quản lý chuỗi một cách tự động.
     */
    public UserStreak recordStudyDay(String userId, int newWordsMemorized) {
        UserStreak userStreak = getUserStreak(userId);
        LocalDate today = LocalDate.now();
        
        if (userStreak.getLastActiveDay() == null) {
            // Ngày đầu tiên học
            userStreak.setCurrentStreak(1);
            userStreak.setNumMemorize(newWordsMemorized);
        } else if (userStreak.getLastActiveDay().isEqual(today.minusDays(1))) {
            // Học liên tục ngày hôm qua -> hôm nay
            userStreak.setCurrentStreak(userStreak.getCurrentStreak() + 1);
            userStreak.setNumMemorize(userStreak.getNumMemorize() + newWordsMemorized);
        } else if (userStreak.getLastActiveDay().isBefore(today.minusDays(1))) {
            // Bị đứt chuỗi (trước hôm qua)
            userStreak.setCurrentStreak(1);
            userStreak.setNumMemorize(newWordsMemorized);
        } else if (userStreak.getLastActiveDay().isEqual(today)) {
            // Học nhiều lần trong ngày
            userStreak.setNumMemorize(userStreak.getNumMemorize() + newWordsMemorized);
        }
        
        userStreak.setLastActiveDay(today);
        userStreak.setUpdatedAt(LocalDateTime.now());
        
        checkAndUpdateLongestStreak(userStreak);
        
        return userStreakRepository.save(userStreak);
    }
}
