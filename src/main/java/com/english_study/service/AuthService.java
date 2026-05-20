package com.english_study.service;

import com.english_study.model.dto.UserDTO;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.RegisterRequest;
import com.english_study.model.response.TokenResponse;
import com.english_study.model.role.Role;
import com.english_study.sercurity.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;


    public TokenResponse login(LoginRequest request) {

        UserDTO userDTO = userService.checkUser(
                request.username(),
                request.password()
        );

        String refreshToken = jwtUtil.generateRefreshToken(userDTO.getId());

        String accessToken = jwtUtil.generateToken(userDTO);

        userService.saveRefreshToken(userDTO, refreshToken);

        return new TokenResponse(
                refreshToken,
                accessToken,
                userDTO
        );
    }

    public UserDTO register(RegisterRequest request) {

        return userService.create(
                request.email(),
                request.fullName(),
                request.username(),
                request.password(),
                Role.USER.getAuthority()
        );
    }

    public TokenResponse refreshToken(String refreshToken) {

        jwtUtil.validateRefreshToken(refreshToken);

        String userId = jwtUtil.extractUserID(refreshToken);

        UserDTO userDTO = userService.getUserByID(userId);

        String newAccessToken = jwtUtil.generateToken(userDTO);

        return new TokenResponse(
                refreshToken,
                newAccessToken,
                userDTO
        );
    }
}