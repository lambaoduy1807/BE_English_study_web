package com.english_study.controller.admin;

import com.english_study.model.dto.WordDTO;
import com.english_study.model.response.ApiResponse;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminVocabSetController {

    private final WordService wordService;

    // Lấy danh sách từ vựng theo bộ từ
    @GetMapping("/vocab-sets/{setId}/words")
    public ApiResponse getWordsBySet(@PathVariable String setId) {
        List<WordDTO> words = wordService.getByVocabId(setId);
        return ApiResponse.success(words, "Lấy danh sách từ vựng thành công");
    }

    // Thêm một từ vựng mới vào bộ từ
    @PostMapping("/vocab-sets/{setId}/words")
    public ApiResponse createWord(@PathVariable String setId, @RequestBody WordDTO dto, @RequestParam(defaultValue = "check") String action) {
        dto.setVocabID(setId);
        WordDTO created = wordService.create(dto, action);
        return ApiResponse.success(created, "Thêm từ vựng thành công");
    }

    // Cập nhật từ vựng
    @PutMapping("/words/{wordId}")
    public ApiResponse updateWord(@PathVariable String wordId, @RequestBody WordDTO dto) {
        WordDTO updated = wordService.update(wordId, dto);
        if (updated == null) {
            return ApiResponse.error(404, "Không tìm thấy từ vựng");
        }
        return ApiResponse.success(updated, "Cập nhật từ vựng thành công");
    }

    // Xóa từ vựng
    @DeleteMapping("/words/{wordId}")
    public ApiResponse deleteWord(@PathVariable String wordId) {
        wordService.delete(wordId);
        return ApiResponse.success(null, "Xóa từ vựng thành công");
    }

    // Tải template file excel
    @GetMapping("/vocab-sets/excel-template")
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
    @PostMapping("/vocab-sets/{setId}/words/excel")
    public ApiResponse importWordsFromExcel(@PathVariable String setId, 
                                            @RequestParam("file") MultipartFile file, 
                                            @RequestParam(defaultValue = "check") String action,
                                            @RequestParam(required = false) List<String> wordsToReplace) {
        try {
            wordService.importWordsFromExcel(setId, file, action, wordsToReplace);
            return ApiResponse.success(null, "Nhập dữ liệu từ Excel thành công");
        } catch (IOException e) {
            return ApiResponse.error(500, "Lỗi khi xử lý file Excel: " + e.getMessage());
        }
    }
}
