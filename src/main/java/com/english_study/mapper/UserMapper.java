package com.english_study.mapper;

import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(UserEntity entity) {
        if (entity == null) return null;

        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail()).fullName(entity.getFullName())
                .isDelete(entity.isDelete())
                .avatar(entity.getAvatar())
                .roleId(entity.getRoleId())
                .beginStreak(entity.getBeginStreak())
                .endStreak(entity.getEndStreak())
                .level(entity.getLevel())
                .totalXP(entity.getTotalXP())
                .refreshToken(entity.getRefreshToken())
                .my_vocabs(entity.getMy_vocabs())
                .authProvider(entity.getAuthProvider())
                .isEmailVerified(entity.isEmailVerified())
                .build();
    }

    public UserEntity toUserEntity(UserDTO dto) {
        if (dto == null) return null;

        return UserEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .isDelete(dto.isDelete())
                .avatar(dto.getAvatar())
                .roleId(dto.getRoleId())
                .beginStreak(dto.getBeginStreak())
                .endStreak(dto.getEndStreak())
                .level(dto.getLevel())
                .totalXP(dto.getTotalXP())
                .refreshToken(dto.getRefreshToken())
                .my_vocabs(dto.getMy_vocabs())
                .authProvider(dto.getAuthProvider())
                .isEmailVerified(dto.isEmailVerified())
                .build();
    }
}