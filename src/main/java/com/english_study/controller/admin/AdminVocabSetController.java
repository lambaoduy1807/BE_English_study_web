package com.english_study.controller.admin;

import com.english_study.model.dto.WordDTO;
import org.springframework.http.ResponseEntity;
import com.english_study.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/vocab-set")
@RequiredArgsConstructor
public class AdminVocabSetController {

    private final WordService wordService;

    // Lấy danh sách từ vựng theo bộ từ
    @GetMapping("/get-words/{setId}")
    public ResponseEntity<?> getWordsBySet(@PathVariable String setId) {
        List<WordDTO> words = wordService.getByVocabId(setId);
        return ResponseEntity.ok(words);
    }

    // Thêm một từ vựng mới vào bộ từ
    @PostMapping("/create-word/{setId}")
    public ResponseEntity<?> createWord(@PathVariable String setId, @RequestBody WordDTO dto, @RequestParam(defaultValue = "check") String action) {
        dto.setVocabID(setId);
        WordDTO created = wordService.create(dto, action);
        return ResponseEntity.ok(created);
    }

    // Cập nhật từ vựng
    @PutMapping("/update-word/{wordId}")
    public ResponseEntity<?> updateWord(@PathVariable String wordId, @RequestBody WordDTO dto) {
        WordDTO updated = wordService.update(wordId, dto);
        return ResponseEntity.ok(updated);
    }

    // Xóa từ vựng
    @DeleteMapping("/delete-word/{wordId}")
    public ResponseEntity<?> deleteWord(@PathVariable String wordId) {
        wordService.delete(wordId);
        return ResponseEntity.ok().build();
    }

    // Tải template file excel
    @GetMapping("/excel-template")
    public ResponseEntity<byte[]> getExcelTemplate() {
        try {
            byte[] fileContent = wordService.generateExcelTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "Vocabulary_Template.xlsx");
            return ResponseEntity.ok().headers(headers).body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Upload file excel thêm từ vựng vào bộ
    @PostMapping("/import-words/{setId}")
    public ResponseEntity<?> importWordsFromExcel(@PathVariable String setId, 
                                            @RequestParam("file") MultipartFile file, 
                                            @RequestParam(defaultValue = "check") String action,
                                            @RequestParam(required = false) List<String> wordsToReplace) throws IOException {
        wordService.importWordsFromExcel(setId, file, action, wordsToReplace);
        return ResponseEntity.ok().build();
    }
}
