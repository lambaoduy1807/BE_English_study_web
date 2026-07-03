package com.english_study.controller;

import com.english_study.model.dto.VideoDTO;
import com.english_study.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@AllArgsConstructor
public class VideoController {

    private final VideoService service;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        VideoDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get-transcript/{videoid}")
    public ResponseEntity<?> getTranscript(@PathVariable String videoid) {
        VideoDTO video = service.getById(videoid);
        if (video != null && video.getTranscripts() != null && !video.getTranscripts().isEmpty()) {
            service.incrementViewCount(videoid);
            return ResponseEntity.ok(video.getTranscripts());
        }
        
        return ResponseEntity.ok(List.of());
    }
}
