package com.english_study.controller;

import com.english_study.model.dto.VideoDTO;
import com.english_study.service.VideoService;
import lombok.AllArgsConstructor;
import com.english_study.model.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@AllArgsConstructor
public class VideoController {

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

    @GetMapping("/get-transcript/{videoid}")
    public ApiResponse getTranscript(@PathVariable String videoid) {
        VideoDTO video = service.getById(videoid);
        if (video != null && video.getTranscripts() != null && !video.getTranscripts().isEmpty()) {
            service.incrementViewCount(videoid);
            return ApiResponse.success(video.getTranscripts(), "Lấy transcript thủ công thành công");
        }
        
        return ApiResponse.success(List.of(), "Không có transcript");
    }
}
