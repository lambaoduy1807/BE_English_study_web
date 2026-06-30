package com.english_study.controller;

import com.english_study.model.dto.WordDTO;
import com.english_study.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@AllArgsConstructor
public class WordController {

    private final WordService service;

    @GetMapping
    public ResponseEntity<List<WordDTO>> getAll(@RequestParam(required = false) String setId) {
        if (setId != null && !setId.isEmpty()) {
            return ResponseEntity.ok(service.getByVocabId(setId));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WordDTO> getById(@PathVariable String id) {
        WordDTO dto = service.getById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }
}
