package com.english_study.service;

import com.english_study.model.entity.IconEntity;
import java.util.List;

public interface IconService {
    List<IconEntity> getAllIcons();
    IconEntity createIcon(String name, String code);
    void deleteIcon(String id);
}
