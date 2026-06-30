package com.english_study.controller;

import com.english_study.model.dto.VocabSetDTO;
import com.english_study.service.VocabSetService;
import lombok.AllArgsConstructor;
import com.english_study.model.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocab-set")
@AllArgsConstructor
public class VocabSetController {

    private final VocabSetService service;

    @GetMapping("/get-all")
    public ApiResponse getAll() {
        return ApiResponse.success(service.getAll(), "Lấy danh sách bộ từ vựng thành công");
    }

    @GetMapping("/get/{id}")
    public ApiResponse getById(@PathVariable String id) {
        VocabSetDTO dto = service.getById(id);
        if (dto == null) {
            return ApiResponse.error(404, "Không tìm thấy bộ từ vựng");
        }
        return ApiResponse.success(dto, "Lấy chi tiết bộ từ vựng thành công");
    }

    @PostMapping("/create")
    public ApiResponse create(@RequestBody VocabSetDTO dto) {
        return ApiResponse.success(service.create(dto), "Tạo bộ từ vựng thành công");
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable String id, @RequestBody VocabSetDTO dto) {
        VocabSetDTO updated = service.update(id, dto);
        if (updated == null) {
            return ApiResponse.error(404, "Không tìm thấy bộ từ vựng để cập nhật");
        }
        return ApiResponse.success(updated, "Cập nhật bộ từ vựng thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success(null, "Xóa bộ từ vựng thành công");
    }
}
