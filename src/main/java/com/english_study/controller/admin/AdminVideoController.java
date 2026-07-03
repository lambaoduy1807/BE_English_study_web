package com.english_study.controller.admin;

import com.english_study.model.dto.VideoDTO;
import org.springframework.http.ResponseEntity;
import com.english_study.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/video")
@RequiredArgsConstructor
public class AdminVideoController {

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

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody VideoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody VideoDTO dto) {
        VideoDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
