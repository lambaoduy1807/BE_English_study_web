package com.english_study.controller.admin;

import org.springframework.http.ResponseEntity;
import com.english_study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<?> updateUserRole(@PathVariable String id, @RequestParam String roleId) {
        return ResponseEntity.ok(userService.updateUserRole(id, roleId));
    }

    @PutMapping("/toggle-block/{id}")
    public ResponseEntity<?> toggleUserBlock(@PathVariable String id) {
        return ResponseEntity.ok(userService.toggleUserBlock(id));
    }
}
