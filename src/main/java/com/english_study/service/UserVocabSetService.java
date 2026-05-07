package com.english_study.service;

import com.english_study.mapper.UserVocabSetMapper;
import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.model.entity.UserVocabSet;
import com.english_study.repository.UserVocabSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserVocabSetService {

    private final UserVocabSetRepository repository;
    private final UserVocabSetMapper mapper;

    public List<UserVocabSetDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserVocabSetDTO getById(String id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    public UserVocabSetDTO create(UserVocabSetDTO dto) {
        UserVocabSet entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public UserVocabSetDTO update(String id, UserVocabSetDTO dto) {
        if (!repository.existsById(id)) {
            return null;
        }
        UserVocabSet entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
