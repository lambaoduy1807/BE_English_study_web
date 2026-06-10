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
}
