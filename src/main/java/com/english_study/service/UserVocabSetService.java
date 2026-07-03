package com.english_study.service;

import com.english_study.mapper.UserVocabSetMapper;
import com.english_study.model.dto.UserVocabSetDTO;
import com.english_study.model.entity.UserVocabSet;
import com.english_study.repository.UserVocabSetRepository;
import com.english_study.repository.VocabSetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.english_study.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserVocabSetService {

    private final UserVocabSetRepository repository;
    private final VocabSetRepository vocabSetRepository;
    private final UserVocabSetMapper mapper;

    public List<UserVocabSetDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<UserVocabSetDTO> getByUserId(String userId) {
        List<UserVocabSet> userDecks = repository.findByUserID(userId);

        return userDecks.stream().map(deck -> {
            UserVocabSetDTO dto = new UserVocabSetDTO();
            dto.setId(deck.getId());
            dto.setUserID(deck.getUserID());
            dto.setVocabID(deck.getVocabID());
            dto.setLearningProgress(deck.getLearningProgress());
            dto.setMemoryWords(deck.getMemoryWords());

            vocabSetRepository.findById(deck.getVocabID()).ifPresent(vocab -> {
                dto.setTitle(vocab.getTitle());
                dto.setIcon(vocab.getIcon());
                dto.setNumOfWords(vocab.getNumOfWords());
            });

            return dto;
        }).collect(Collectors.toList());
    }

    public UserVocabSetDTO getById(String id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bộ từ vựng của người dùng"));
    }

    public UserVocabSetDTO create(UserVocabSetDTO dto) {
        UserVocabSet entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    public UserVocabSetDTO update(String id, UserVocabSetDTO dto) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy bộ từ vựng để cập nhật");
        }
        UserVocabSet entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public UserVocabSetDTO recordStudySession(String userId, String vocabId, int newWords, int xpGained, int progress, List<String> memoryWords, List<String> clozeWords) {
        UserVocabSet userVocabSet = repository.findByUserIDAndVocabID(userId, vocabId);
        if (userVocabSet == null) {
            userVocabSet = UserVocabSet.builder()
                    .userID(userId)
                    .vocabID(vocabId)
                    .learningProgress(0)
                    .numMemorizeNew(0)
                    .xpNew(0)
                    .memoryWords(new java.util.ArrayList<>())
                    .clozeWords(new java.util.ArrayList<>())
                    .build();
        }

        if (progress > userVocabSet.getLearningProgress()) {
            userVocabSet.setLearningProgress(progress);
        }

        if (memoryWords != null) {
            userVocabSet.setMemoryWords(memoryWords);
        }

        if (clozeWords != null) {
            userVocabSet.setClozeWords(clozeWords);
        }

        userVocabSet.setNumMemorizeNew(userVocabSet.getNumMemorizeNew() + newWords);
        userVocabSet.setXpNew(userVocabSet.getXpNew() + xpGained);
        userVocabSet.setUpdatedAt(java.time.LocalDateTime.now());
        
        return mapper.toDTO(repository.save(userVocabSet));
    }
}
