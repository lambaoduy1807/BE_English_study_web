package com.english_study.controller;

import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.RefreshTokenRequest;
import com.english_study.model.request.RegisterRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Validated
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.success(authService.login(loginRequest), "Login successful");
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest) {
        return ApiResponse.success(authService.register(registerRequest), "Register successful");
    }

    @PostMapping("/refresh")
    public ApiResponse refresh(@RequestBody RefreshTokenRequest refreshToken) {
        return ApiResponse.success(authService.refreshToken(refreshToken.refreshToken()), "Renew access token success");
    }

    @GetMapping("/verify-email")
    public ApiResponse verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ApiResponse.success(null, "Email verified successfully");
    }

    @PostMapping("/resend-verification")
    public ApiResponse resendVerification(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authService.resendVerificationEmail(email);
        return ApiResponse.success(null, "Verification email resent successfully");
    }

    @PostMapping("/forgot-password")
    public ApiResponse forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authService.forgotPassword(email);
        return ApiResponse.success(null, "Password reset email sent successfully");
    }

    @PostMapping("/reset-password")
    public ApiResponse resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        authService.resetPassword(token, newPassword);
        return ApiResponse.success(null, "Password reset successfully");
    }

    @PostMapping("/google")
    public ApiResponse googleLogin(@RequestBody Map<String, String> request) {
        String idToken = request.get("idToken");
        return ApiResponse.success(authService.googleLogin(idToken), "Google login successful");
    }
}
