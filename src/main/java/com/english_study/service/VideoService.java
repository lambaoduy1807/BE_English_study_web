package com.english_study.service;

import com.english_study.mapper.VideoMapper;
import com.english_study.model.dto.VideoDTO;
import com.english_study.model.entity.Video;
import com.english_study.repository.VideoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VideoService {

    private final VideoRepository repository;
    private final VideoMapper mapper;

    public List<VideoDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public VideoDTO getById(String id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    public VideoDTO create(VideoDTO dto) {
        Video entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public VideoDTO update(String id, VideoDTO dto) {
        if (!repository.existsById(id)) {
            return null;
        }
        Video entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
