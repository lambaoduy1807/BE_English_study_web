package com.english_study.mapper;

import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toUserDTO(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .address(entity.getAddress())
                .roleName(entity.getRoleId()) // Default mapping roleId to roleName
                .orderIds(entity.getOrderIds())
                .favoriteProductIds(entity.getFavoriteProductIds())
                .build();
    }

    public UserEntity toUserEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setAddress(dto.getAddress());
        entity.setRoleId(dto.getRoleName());
        entity.setOrderIds(dto.getOrderIds());
        entity.setFavoriteProductIds(dto.getFavoriteProductIds());
        return entity;
    }
}
