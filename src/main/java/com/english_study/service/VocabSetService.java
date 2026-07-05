package com.english_study.service;

import com.english_study.mapper.VocabSetMapper;
import com.english_study.model.dto.VocabSetDTO;
import com.english_study.model.entity.VocabSetEntity;
import com.english_study.repository.VocabSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.english_study.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VocabSetService {

    private final VocabSetRepository repository;
    private final VocabSetMapper mapper;

    public List<VocabSetDTO> getAll() {
        return repository.findByCreatedByIsNull().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public VocabSetDTO getById(String id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bộ từ vựng"));
    }

    public VocabSetDTO create(VocabSetDTO dto) {
        VocabSetEntity entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public VocabSetDTO update(String id, VocabSetDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy bộ từ vựng để cập nhật");
        }
        VocabSetEntity entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
