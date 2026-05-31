package com.english_study.controller;

import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.service.UserVocabSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.Authentication;
import com.english_study.model.JwtUserPrincipal;

@RestController
@RequestMapping("/api/user-vocab-sets")
@AllArgsConstructor
public class UserVocabSetController {

    private final UserVocabSetService service;

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
