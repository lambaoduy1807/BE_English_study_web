package com.english_study.controller;

import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.service.UserVocabSetService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import com.english_study.model.JwtUserPrincipal;

import com.english_study.model.request.StudySessionRequest;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> recordStudySession(@RequestBody StudySessionRequest request) {
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
        
        return ResponseEntity.ok(updatedSet);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserPrincipal) {
            String userId = ((JwtUserPrincipal) authentication.getPrincipal()).getUserId();
            return ResponseEntity.ok(service.getByUserId(userId));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        UserVocabSetDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserVocabSetDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UserVocabSetDTO dto) {
        UserVocabSetDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
