package com.english_study.service;

import com.english_study.mapper.WordMapper;
import com.english_study.model.dto.WordDTO;
import com.english_study.model.entity.Word;
import com.english_study.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository repository;
    private final WordMapper mapper;

    public List<WordDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<WordDTO> getByVocabId(String vocabID) {
        return repository.findByVocabID(vocabID).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public WordDTO getById(String id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    public WordDTO create(WordDTO dto) {
        Word entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public WordDTO update(String id, WordDTO dto) {
        if (!repository.existsById(id)) {
            return null;
        }
        Word entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public byte[] generateExcelTemplate() throws java.io.IOException {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
             java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream()) {
            
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Vocabulary Template");
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            
            String[] columns = {"Word", "Mean", "Type", "Example"};
            for (int i = 0; i < columns.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            
            workbook.write(out);
            return out.toByteArray();
        }
    }

    public void importWordsFromExcel(String vocabID, org.springframework.web.multipart.MultipartFile file) throws java.io.IOException {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(file.getInputStream())) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            java.util.Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.iterator();
            
            int rowNumber = 0;
            java.util.List<Word> wordsToSave = new java.util.ArrayList<>();
            
            while (rows.hasNext()) {
                org.apache.poi.ss.usermodel.Row currentRow = rows.next();
                
                // Skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                
                String wordText = getCellValue(currentRow.getCell(0));
                if (wordText == null || wordText.trim().isEmpty()) {
                    continue; // Skip empty rows
                }
                
                String mean = getCellValue(currentRow.getCell(1));
                String type = getCellValue(currentRow.getCell(2));
                String example = getCellValue(currentRow.getCell(3));
                
                Word word = Word.builder()
                        .word(wordText)
                        .mean(mean)
                        .type(type)
                        .example(example)
                        .vocabID(vocabID)
                        .build();
                        
                wordsToSave.add(word);
            }
            
            if (!wordsToSave.isEmpty()) {
                repository.saveAll(wordsToSave);
            }
        }
    }
    
    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
