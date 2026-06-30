package com.english_study.controller;

import com.english_study.model.dto.CategoryDTO;
import com.english_study.service.CategoryService;
import lombok.AllArgsConstructor;
import com.english_study.model.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/get-all")
    public ApiResponse getAll() {
        return ApiResponse.success(service.getAll(), "Lấy danh sách danh mục thành công");
    }

    @GetMapping("/get/{id}")
    public ApiResponse getById(@PathVariable Integer id) {
        CategoryDTO dto = service.getById(id);
        if (dto == null) {
            return ApiResponse.error(404, "Không tìm thấy danh mục");
        }
        return ApiResponse.success(dto, "Lấy danh mục thành công");
    }
}
