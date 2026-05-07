package com.english_study.controller;

import com.english_study.model.dto.VocabSetDTO;
import com.english_study.service.VocabSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocab-sets")
@AllArgsConstructor
public class VocabSetController {

    private final VocabSetService service;

    @GetMapping
    public ResponseEntity<List<VocabSetDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VocabSetDTO> getById(@PathVariable String id) {
        VocabSetDTO dto = service.getById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<VocabSetDTO> create(@RequestBody VocabSetDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VocabSetDTO> update(@PathVariable String id, @RequestBody VocabSetDTO dto) {
        VocabSetDTO updated = service.update(id, dto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
