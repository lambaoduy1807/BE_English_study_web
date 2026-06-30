package com.english_study.controller.admin;

import com.english_study.mapper.IconMapper;
import com.english_study.model.dto.IconDTO;
import com.english_study.model.entity.IconEntity;
import com.english_study.model.response.ApiResponse;
import com.english_study.service.IconService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/icon")
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;
    private final IconMapper iconMapper;

    @GetMapping("/get-all")
    public ApiResponse<List<IconDTO>> getAllIcons() {
        List<IconDTO> dtos = iconService.getAllIcons().stream().map(iconMapper::toDTO).toList();
        return ApiResponse.success(dtos, "Lấy danh sách icon thành công");
    }

    @PostMapping("/create")
    public ApiResponse<IconDTO> createIcon(@RequestBody CreateIconRequest request) {
        IconEntity icon = iconService.createIcon(request.getName(), request.getCode());
        return ApiResponse.success(iconMapper.toDTO(icon), "Tạo icon thành công");
    }

    @DeleteMapping("/delete/{id}")
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
