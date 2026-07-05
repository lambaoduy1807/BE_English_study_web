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
    private final WordService wordService;
    private final VocabSetService vocabSetService;

    public byte[] generateExcelTemplate() throws java.io.IOException {
        return wordService.generateExcelTemplate();
    }

    public List<com.english_study.model.dto.WordDTO> parseWordsFromExcel(org.springframework.web.multipart.MultipartFile file) throws java.io.IOException {
        return wordService.parseWordsFromExcel(file);
    }

    public UserVocabSetDTO addSystemVocabSet(String userId, String vocabId) {
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
                    .updatedAt(java.time.LocalDateTime.now())
                    .build();
            return mapper.toDTO(repository.save(userVocabSet));
        }
        return mapper.toDTO(userVocabSet);
    }

    public UserVocabSetDTO createCustomVocabSet(String userId, com.english_study.model.request.CustomVocabSetRequest request) {
        com.english_study.model.dto.VocabSetDTO vocabSetDTO = request.getVocabSet();
        vocabSetDTO.setCreatedBy(userId);
        com.english_study.model.dto.VocabSetDTO createdVocabSet = vocabSetService.create(vocabSetDTO);

        if (request.getWords() != null && !request.getWords().isEmpty()) {
            wordService.createWords(createdVocabSet.getId(), request.getWords());
        }

        UserVocabSet userVocabSet = UserVocabSet.builder()
                .userID(userId)
                .vocabID(createdVocabSet.getId())
                .learningProgress(0)
                .numMemorizeNew(0)
                .xpNew(0)
                .memoryWords(new java.util.ArrayList<>())
                .clozeWords(new java.util.ArrayList<>())
                .updatedAt(java.time.LocalDateTime.now())
                .build();

        return mapper.toDTO(repository.save(userVocabSet));
    }

    public UserVocabSetDTO updateCustomVocabSet(String userId, String vocabId, com.english_study.model.request.CustomVocabSetRequest request) {
        com.english_study.model.entity.VocabSetEntity existing = vocabSetRepository.findById(vocabId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bộ từ vựng"));
        if (!userId.equals(existing.getCreatedBy())) {
            throw new RuntimeException("Bạn không có quyền cập nhật bộ từ này");
        }

        com.english_study.model.dto.VocabSetDTO vocabSetDTO = request.getVocabSet();
        vocabSetDTO.setCreatedBy(userId);
        vocabSetService.update(vocabId, vocabSetDTO);

        if (request.getWords() != null) {
            wordService.updateWordsForVocabSet(vocabId, request.getWords());
        }

        UserVocabSet userVocabSet = repository.findByUserIDAndVocabID(userId, vocabId);
        if (userVocabSet != null) {
            return mapper.toDTO(userVocabSet);
        }
        return null;
    }

    public void deleteCustomVocabSet(String userId, String vocabId) {
        com.english_study.model.entity.VocabSetEntity existing = vocabSetRepository.findById(vocabId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bộ từ vựng"));
        if (!userId.equals(existing.getCreatedBy())) {
            throw new RuntimeException("Bạn không có quyền xoá bộ từ này");
        }

        wordService.updateWordsForVocabSet(vocabId, new java.util.ArrayList<>()); // delete words
        vocabSetService.delete(vocabId);
        
        UserVocabSet userVocabSet = repository.findByUserIDAndVocabID(userId, vocabId);
        if (userVocabSet != null) {
            repository.delete(userVocabSet);
        }
    }

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
