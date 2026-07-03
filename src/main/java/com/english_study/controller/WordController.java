package com.english_study.controller;

import com.english_study.model.dto.WordDTO;
import com.english_study.service.WordService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/word")
@AllArgsConstructor
public class WordController {

    private final WordService service;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String setId) {
        if (setId != null && !setId.isEmpty()) {
            return ResponseEntity.ok(service.getByVocabId(setId));
        }
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        WordDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }
}
