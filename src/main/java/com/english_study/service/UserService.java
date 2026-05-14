package com.english_study.service;

import com.english_study.exception.UserAlreadyExistsException;
import com.english_study.exception.UserNotFoundException;
// Khuyến nghị tạo thêm InvalidCredentialsException cho lỗi sai mật khẩu
import com.english_study.mapper.UserMapper;
import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO checkUser(String username, String password) {
        UserEntity user = userRepository.findByName(username);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException("Sai tên đăng nhập hoặc mật khẩu");
        }

        return userMapper.toUserDTO(user);
    }

    @Transactional
    public void saveRefreshToken(UserDTO userDTO, String refreshToken) {
        userRepository.updateRefreshtoken(userDTO.getId(),refreshToken);
    }

    @Transactional
    public UserDTO create(String email, String fullName, String name, String password, String role) {
        if (userRepository.existsByName(name)) {
            throw new UserAlreadyExistsException("Tên đăng nhập đã tồn tại!");
        }

        UserEntity user = new UserEntity(name, email, passwordEncoder.encode(password), fullName, role);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    public UserDTO getUserByID(String id) {
        // Tối ưu 6: Tận dụng Optional.map() của Java 8+ để code clean và gọn hơn
        return userRepository.findById(id)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}