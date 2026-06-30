package com.english_study.controller;

import com.english_study.model.dto.WordDTO;
import com.english_study.service.WordService;
import lombok.AllArgsConstructor;
import com.english_study.model.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/word")
@AllArgsConstructor
public class WordController {

    private final WordService service;

    @GetMapping("/get-all")
    public ApiResponse getAll(@RequestParam(required = false) String setId) {
        if (setId != null && !setId.isEmpty()) {
            return ApiResponse.success(service.getByVocabId(setId), "Lấy danh sách từ theo bộ thành công");
        }
        return ApiResponse.success(service.getAll(), "Lấy danh sách từ thành công");
    }

    @GetMapping("/get/{id}")
    public ApiResponse getById(@PathVariable String id) {
        WordDTO dto = service.getById(id);
        if (dto == null) {
            return ApiResponse.error(404, "Không tìm thấy từ");
        }
        return ApiResponse.success(dto, "Lấy chi tiết từ thành công");
    }
}
