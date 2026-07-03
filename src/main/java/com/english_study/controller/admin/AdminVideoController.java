package com.english_study.controller.admin;

import com.english_study.model.dto.VideoDTO;
import com.english_study.model.response.ApiResponse;
import com.english_study.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/video")
@RequiredArgsConstructor
public class AdminVideoController {

    private final VideoService service;

    @GetMapping("/get-all")
    public ApiResponse getAll() {
        return ApiResponse.success(service.getAll(), "Lấy danh sách video thành công");
    }

    @GetMapping("/get/{id}")
    public ApiResponse getById(@PathVariable String id) {
        VideoDTO dto = service.getById(id);
        if (dto == null) {
            return ApiResponse.error(404, "Không tìm thấy video");
        }
        return ApiResponse.success(dto, "Lấy chi tiết video thành công");
    }

    @PostMapping("/create")
    public ApiResponse create(@RequestBody VideoDTO dto) {
        return ApiResponse.success(service.create(dto), "Tạo video thành công");
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable String id, @RequestBody VideoDTO dto) {
        VideoDTO updated = service.update(id, dto);
        if (updated == null) {
            return ApiResponse.error(404, "Không tìm thấy video để cập nhật");
        }
        return ApiResponse.success(updated, "Cập nhật video thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable String id) {
        service.delete(id);
        return ApiResponse.success(null, "Xóa video thành công");
    }
}
