package com.english_study.controller.admin;

import com.english_study.mapper.IconMapper;
import com.english_study.model.dto.IconDTO;
import com.english_study.model.entity.IconEntity;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<IconDTO>> getAllIcons() {
        List<IconDTO> dtos = iconService.getAllIcons().stream().map(iconMapper::toDTO).toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/create")
    public ResponseEntity<IconDTO> createIcon(@RequestBody CreateIconRequest request) {
        IconEntity icon = iconService.createIcon(request.getName(), request.getCode());
        return ResponseEntity.ok(iconMapper.toDTO(icon));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIcon(@PathVariable String id) {
        iconService.deleteIcon(id);
        return ResponseEntity.ok().build();
    }

    @Data
    public static class CreateIconRequest {
        private String name;
        private String code;
    }
}
