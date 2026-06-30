package com.english_study.service;

import com.english_study.mapper.WordMapper;
import com.english_study.model.dto.WordDTO;
import com.english_study.model.entity.VocabSetEntity;
import com.english_study.model.entity.Word;
import com.english_study.repository.VocabSetRepository;
import com.english_study.repository.WordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WordService {

    private final WordRepository repository;
    private final VocabSetRepository vocabSetRepository;
    private final WordMapper mapper;

    private void updateVocabSetWordCount(String vocabID) {
        VocabSetEntity vocabSet = vocabSetRepository.findById(vocabID).orElse(null);
        if (vocabSet != null) {
            int count = repository.findByVocabID(vocabID).size();
            vocabSet.setNumOfWords(count);
            vocabSetRepository.save(vocabSet);
        }
    }

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

    public WordDTO create(WordDTO dto, String action) {
        dto.setWord(dto.getWord().trim().toLowerCase());
        Word existing = repository.findByVocabIDAndWord(dto.getVocabID(), dto.getWord());
        if (existing != null) {
            if ("check".equalsIgnoreCase(action)) {
                java.util.List<com.english_study.model.dto.ConflictInfo> conflicts = new java.util.ArrayList<>();
                conflicts.add(new com.english_study.model.dto.ConflictInfo(mapper.toDTO(existing), dto));
                throw new com.english_study.exception.ConflictWordException("Từ vựng đã tồn tại", conflicts);
            } else if ("replace".equalsIgnoreCase(action)) {
                return update(existing.getId(), dto);
            }
        }
        
        Word entity = mapper.toEntity(dto);
        Word saved = repository.save(entity);
        updateVocabSetWordCount(dto.getVocabID());
        return mapper.toDTO(saved);
    }

    public WordDTO update(String id, WordDTO dto) {
        if (!repository.existsById(id)) {
            return null;
        }
        dto.setWord(dto.getWord().trim().toLowerCase());
        Word entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(String id) {
        Word word = repository.findById(id).orElse(null);
        if (word != null) {
            repository.deleteById(id);
            updateVocabSetWordCount(word.getVocabID());
        }
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

    public void importWordsFromExcel(String vocabID, org.springframework.web.multipart.MultipartFile file, String action, java.util.List<String> wordsToReplace) throws java.io.IOException {
        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(file.getInputStream())) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
            java.util.Iterator<org.apache.poi.ss.usermodel.Row> rows = sheet.iterator();
            
            int rowNumber = 0;
            java.util.List<Word> wordsToSave = new java.util.ArrayList<>();
            java.util.List<com.english_study.model.dto.ConflictInfo> conflicts = new java.util.ArrayList<>();
            
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
                wordText = wordText.trim().toLowerCase();
                
                String mean = getCellValue(currentRow.getCell(1));
                String type = getCellValue(currentRow.getCell(2));
                String example = getCellValue(currentRow.getCell(3));
                
                Word existing = repository.findByVocabIDAndWord(vocabID, wordText);
                System.out.println("Row " + rowNumber + ": wordText='" + wordText + "', existing=" + (existing != null));
                
                if (existing != null) {
                    if ("check".equalsIgnoreCase(action)) {
                        WordDTO newWordDto = new WordDTO(null, wordText, mean, type, example, vocabID);
                        conflicts.add(new com.english_study.model.dto.ConflictInfo(mapper.toDTO(existing), newWordDto));
                        continue;
                    } else if ("replace".equalsIgnoreCase(action)) {
                        boolean willReplace = (wordsToReplace != null && wordsToReplace.contains(wordText));
                        System.out.println("  replace action. willReplace=" + willReplace + " (wordsToReplace=" + wordsToReplace + ")");
                        if (willReplace) {
                            existing.setMean(mean);
                            existing.setType(type);
                            existing.setExample(example);
                            wordsToSave.add(existing);
                        }
                        continue;
                    }
                } else {
                    System.out.println("  existing is null. Adding new word: " + wordText);
                    Word word = Word.builder()
                            .word(wordText)
                            .mean(mean)
                            .type(type)
                            .example(example)
                            .vocabID(vocabID)
                            .build();
                            
                    wordsToSave.add(word);
                }
            }
            
            if ("check".equalsIgnoreCase(action) && !conflicts.isEmpty()) {
                throw new com.english_study.exception.ConflictWordException("Phát hiện từ vựng bị trùng lặp", conflicts);
            }
            
            if (!wordsToSave.isEmpty()) {
                repository.saveAll(wordsToSave);
                updateVocabSetWordCount(vocabID);
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
