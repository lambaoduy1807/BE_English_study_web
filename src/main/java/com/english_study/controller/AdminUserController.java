package com.english_study.controller;

import com.english_study.model.response.ApiResponse;
import com.english_study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse getAllUsers() {
        return ApiResponse.success(userService.getAllUsers(), "Lấy danh sách người dùng thành công");
    }

    @PutMapping("/{id}/role")
    public ApiResponse updateUserRole(@PathVariable String id, @RequestParam String roleId) {
        return ApiResponse.success(userService.updateUserRole(id, roleId), "Cập nhật quyền thành công");
    }

    @PutMapping("/{id}/block")
    public ApiResponse toggleUserBlock(@PathVariable String id) {
        return ApiResponse.success(userService.toggleUserBlock(id), "Cập nhật trạng thái khóa thành công");
    }
}
