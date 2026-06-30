package com.english_study.service.impl;

import com.english_study.model.entity.IconEntity;
import com.english_study.repository.IconRepository;
import com.english_study.service.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IconServiceImpl implements IconService {

    private final IconRepository iconRepository;

    @Override
    public List<IconEntity> getAllIcons() {
        return iconRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public IconEntity createIcon(String name, String code) {
        IconEntity icon = IconEntity.builder()
                .name(name)
                .code(code)
                .createdAt(new Date())
                .build();
                
        return iconRepository.save(icon);
    }

    @Override
    public void deleteIcon(String id) {
        iconRepository.deleteById(id);
    }
}
