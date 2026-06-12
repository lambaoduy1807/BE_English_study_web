package com.english_study.controller;

import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.service.UserVocabSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
import com.english_study.model.JwtUserPrincipal;

import com.english_study.model.request.StudySessionRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.UserDailyStatService;
import com.english_study.service.UserService;
import com.english_study.service.UserStreakService;

@RestController
@RequestMapping("/api/user-vocab-sets")
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

    @GetMapping
    public ResponseEntity<List<UserVocabSetDTO>> getAll(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserPrincipal) {
            String userId = ((JwtUserPrincipal) authentication.getPrincipal()).getUserId();
            return ResponseEntity.ok(service.getByUserId(userId));
        }
        return ResponseEntity.ok(service.getAll()); // Fallback an toàn
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserVocabSetDTO> getById(@PathVariable String id) {
        UserVocabSetDTO dto = service.getById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserVocabSetDTO> create(@RequestBody UserVocabSetDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserVocabSetDTO> update(@PathVariable String id, @RequestBody UserVocabSetDTO dto) {
        UserVocabSetDTO updated = service.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
