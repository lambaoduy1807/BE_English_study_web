package com.english_study.controller;

import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.RefreshTokenRequest;
import com.english_study.model.request.RegisterRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
@Validated
public class AuthController {
    AuthService authService;
    @PostMapping("login")
    public ApiResponse login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.success(authService.login(loginRequest), "Login successful");

    }

    @PostMapping("register")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest) {
        return ApiResponse.success(authService.register(registerRequest), "Register successful");
    }
    @PostMapping("refresh")
    public ApiResponse refresh(@RequestBody RefreshTokenRequest refreshToken) {
        return  ApiResponse.success(authService.refreshToken(refreshToken.refreshToken()),"Renew access token success");
    }
}
