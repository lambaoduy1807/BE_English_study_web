package com.english_study.controller.admin;

import com.english_study.model.entity.IconEntity;
import com.english_study.model.response.ApiResponse;
import com.english_study.service.IconService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/icons")
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;

    @GetMapping
    public ApiResponse<List<IconEntity>> getAllIcons() {
        return ApiResponse.success(iconService.getAllIcons(), "Lấy danh sách icon thành công");
    }

    @PostMapping
    public ApiResponse<IconEntity> createIcon(@RequestBody CreateIconRequest request) {
        IconEntity icon = iconService.createIcon(request.getName(), request.getCode());
        return ApiResponse.success(icon, "Tạo icon thành công");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteIcon(@PathVariable String id) {
        iconService.deleteIcon(id);
        return ApiResponse.success(null, "Xóa icon thành công");
    }

    @Data
    public static class CreateIconRequest {
        private String name;
        private String code;
    }
}
