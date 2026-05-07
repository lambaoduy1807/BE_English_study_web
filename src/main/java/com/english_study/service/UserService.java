package com.english_study.service;

import com.english_study.exception.UserNotFoundException;
import com.english_study.mapper.UserMapper;
import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDTO checkUser(String username, String password) {
        UserEntity user = userRepository.findByName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) return userMapper.toUserDTO(user);
        return null;
    }

    public void saveRefreshToken(UserDTO userDTO, String refreshToken) {
        UserEntity userEntity = userMapper.toUserEntity(userDTO);
        userEntity.setRefreshToken(refreshToken);
        userRepository.save(userEntity);
    }


    public UserDTO create(String email, String name, String password, String role) {
        UserEntity user = new UserEntity(name, email, passwordEncoder.encode(password), role);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    public UserDTO getUserByID(String id) {
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found")));
    }
}