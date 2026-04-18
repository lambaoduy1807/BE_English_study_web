package com.english_study.service;

import com.english_study.exception.InvalidCredentialException;
import com.english_study.exception.UserAlreadyExistsException;
import com.english_study.exception.UserNotFoundException;
import com.english_study.mapper.UserMapper;
import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.RegisterRequest;
import com.english_study.model.response.TokenResponse;
import com.english_study.repository.UserRepository;
import com.english_study.sercurity.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final String PHONE_REGEX =
            "^(0|\\+84)[0-9]{9}$";

    public TokenResponse login(LoginRequest loginRequest) {

        UserEntity user = userRepository.findByName(loginRequest.getUsername());

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialException("Password is incorrect");
        }

        String accessToken = jwtUtil.generateToken(userMapper.toUserDTO(user));
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        return new TokenResponse(refreshToken, accessToken, userMapper.toUserDTO(user));
    }

    public UserDTO register(RegisterRequest request) {

        if (userRepository.findByName(request.getName()) != null) {
            throw new UserAlreadyExistsException("Username is already in use");
        }

        String password = passwordEncoder.encode(request.getPassword());
        String defaultRole = "USER"; // Default role for new registrations

        UserEntity res;
        if (request.getEmail_phoneNumber().matches(EMAIL_REGEX)) {
            String email = request.getEmail_phoneNumber();
            res = userRepository.save(new UserEntity(request.getName(), email, password, defaultRole));
        } else if (request.getEmail_phoneNumber().matches(PHONE_REGEX)) {
            try {
                Long phoneNumber = Long.parseLong(request.getEmail_phoneNumber());
                res = userRepository.save(new UserEntity(request.getName(), phoneNumber, password, defaultRole));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid phone number format");
            }
        } else {
            throw new IllegalArgumentException("Email hoặc số điện thoại không hợp lệ");
        }

        return userMapper.toUserDTO(res);
    }
}