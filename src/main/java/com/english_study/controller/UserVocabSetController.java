package com.english_study.controller;

import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.service.UserVocabSetService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import com.english_study.model.JwtUserPrincipal;

import com.english_study.model.request.StudySessionRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.UserDailyStatService;
import com.english_study.service.UserService;
import com.english_study.service.UserStreakService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-vocab-set")
@AllArgsConstructor
public class UserVocabSetController {

    private final UserVocabSetService service;
    private final UserService userService;
    private final UserDailyStatService userDailyStatService;
    private final UserStreakService userStreakService;

    @PostMapping("/study-session")
    public ApiResponse recordStudySession(@RequestBody StudySessionRequest request) {
        String userId = SecurityUtil.getCurrentUserId();
        
        UserVocabSetDTO updatedSet = service.recordStudySession(
            userId, 
            request.getVocabId(), 
            request.getNewWords(), 
            request.getXpGained(), 
            request.getProgress(),
            request.getMemoryWords(),
            request.getClozeWords()
        );
        
        userDailyStatService.recordDailyStat(userId, request.getVocabId(), request.getNewWords(), request.getXpGained());
        userService.addXP(userId, request.getXpGained());
        
        if (request.getNewWords() > 0) {
            userStreakService.recordStudyDay(userId, request.getNewWords());
        }
        
        return ApiResponse.success(updatedSet, "Ghi nhận phiên học thành công");
    }

    @GetMapping("/get-all")
    public ApiResponse getAll(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserPrincipal) {
            String userId = ((JwtUserPrincipal) authentication.getPrincipal()).getUserId();
            return ApiResponse.success(service.getByUserId(userId), "Lấy danh sách bộ từ của người dùng thành công");
        }
        return ApiResponse.success(service.getAll(), "Lấy danh sách bộ từ thành công");
    }

    @GetMapping("/get/{id}")
    public ApiResponse getById(@PathVariable String id) {
        UserVocabSetDTO dto = service.getById(id);
        if (dto == null) {
            return ApiResponse.error(404, "Không tìm thấy bộ từ vựng của người dùng");
        }
        return ApiResponse.success(dto, "Lấy chi tiết thành công");
    }

    @PostMapping("/create")
    public ApiResponse create(@RequestBody UserVocabSetDTO dto) {
        return ApiResponse.success(service.create(dto), "Tạo mới thành công");
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable String id, @RequestBody UserVocabSetDTO dto) {
        UserVocabSetDTO updated = service.update(id, dto);
        if (updated == null) {
            return ApiResponse.error(404, "Không tìm thấy bộ từ vựng để cập nhật");
        }
        return ApiResponse.success(updated, "Cập nhật thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success(null, "Xóa thành công");
    }

}
