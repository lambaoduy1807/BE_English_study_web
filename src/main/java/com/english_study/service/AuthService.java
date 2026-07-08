package com.english_study.service;

import com.english_study.model.dto.UserDTO;
import com.english_study.model.entity.UserEntity;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.RegisterRequest;
import com.english_study.model.response.TokenResponse;
import com.english_study.model.role.Role;
import com.english_study.repository.UserRepository;
import com.english_study.sercurity.jwt.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${Client_ID}")
    private String googleClientId;

    public TokenResponse login(LoginRequest request) {

        UserDTO userDTO = userService.checkUser(
                request.username(),
                request.password()
        );

        String refreshToken = jwtUtil.generateRefreshToken(userDTO.getId());
        String accessToken = jwtUtil.generateToken(userDTO);
        userService.saveRefreshToken(userDTO, refreshToken);

        return new TokenResponse(refreshToken, accessToken, userDTO);
    }

    public boolean checkUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public String register(RegisterRequest request) {
        String verificationToken = UUID.randomUUID().toString();
        
        // userService.create now accepts verificationToken
        userService.create(
                request.email(),
                request.fullName(),
                request.username(),
                request.password(),
                Role.USER.getAuthority(),
                verificationToken
        );

        mailService.sendVerificationEmail(request.email(), verificationToken);
        
        return "Registration successful. Please check your email to verify your account.";
    }

    public void verifyEmail(String token) {
        UserEntity user = userRepository.findByVerificationToken(token);
        if (user == null) {
            throw new RuntimeException("Invalid verification token");
        }
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    public void resendVerificationEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (user.isEmailVerified()) {
            throw new RuntimeException("Email is already verified");
        }
        String newToken = UUID.randomUUID().toString();
        user.setVerificationToken(newToken);
        userRepository.save(user);
        
        mailService.sendVerificationEmail(email, newToken);
    }

    public void forgotPassword(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        String resetToken = UUID.randomUUID().toString();
        user.setResetPasswordToken(resetToken);
        // Expiry 30 minutes
        user.setResetPasswordTokenExpiryDate(new java.util.Date(System.currentTimeMillis() + 30 * 60 * 1000));
        userRepository.save(user);
        
        mailService.sendPasswordResetEmail(email, resetToken);
    }

    public void resetPassword(String token, String newPassword) {
        UserEntity user = userRepository.findByResetPasswordToken(token);
        if (user == null) {
            throw new RuntimeException("Invalid reset token");
        }
        if (user.getResetPasswordTokenExpiryDate().before(new java.util.Date(System.currentTimeMillis()))) {
            throw new RuntimeException("Reset token has expired");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiryDate(null);
        userRepository.save(user);
    }

    public TokenResponse googleLogin(String tokenString) {
        try {
            // Kiểm tra xem tokenString là idToken hay accessToken
            String email;
            String name;
            String pictureUrl;

            if (tokenString.length() > 1000) {
                // Khả năng cao là idToken
                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList(googleClientId))
                        .build();

                GoogleIdToken idToken = verifier.verify(tokenString);
                if (idToken != null) {
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    email = ((String) payload.get("email")).trim().toLowerCase();
                    name = (String) payload.get("name");
                    pictureUrl = (String) payload.get("picture");
                } else {
                    throw new RuntimeException("Invalid Google ID token.");
                }
            } else {
                // Khả năng cao là accessToken từ useGoogleLogin
                org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setBearerAuth(tokenString);
                org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>("", headers);
                
                org.springframework.http.ResponseEntity<java.util.Map> response = restTemplate.exchange(
                        "https://www.googleapis.com/oauth2/v3/userinfo", 
                        org.springframework.http.HttpMethod.GET, 
                        entity, 
                        java.util.Map.class);
                
                java.util.Map<String, Object> payload = response.getBody();
                if (payload == null || !payload.containsKey("email")) {
                    throw new RuntimeException("Failed to fetch Google user info.");
                }
                email = ((String) payload.get("email")).trim().toLowerCase();
                name = (String) payload.get("name");
                pictureUrl = (String) payload.get("picture");
            }

            UserEntity userEntity = userRepository.findByEmail(email);
            UserDTO userDTO;

            if (userEntity == null) {
                // Create new user
                userDTO = userService.createGoogleUser(email, name, pictureUrl);
            } else {
                if (userEntity.isDelete()) {
                    throw new com.english_study.exception.InvalidCredentialException("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.");
                }
                // Đánh dấu email đã xác thực nếu trước đó đăng ký thường nhưng chưa xác thực
                if (!userEntity.isEmailVerified()) {
                    userEntity.setEmailVerified(true);
                    userRepository.save(userEntity);
                }
                userDTO = userService.getUserByID(userEntity.getId());
            }

            String refreshToken = jwtUtil.generateRefreshToken(userDTO.getId());
            String accessToken = jwtUtil.generateToken(userDTO);
            userService.saveRefreshToken(userDTO, refreshToken);

            return new TokenResponse(refreshToken, accessToken, userDTO);

        } catch (com.english_study.exception.InvalidCredentialException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify Google token", e);
        }
    }

    public TokenResponse refreshToken(String refreshToken) {

        jwtUtil.validateRefreshToken(refreshToken);

        String userId = jwtUtil.extractUserID(refreshToken);

        UserDTO userDTO = userService.getUserByID(userId);

        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null && userEntity.isDelete()) {
            throw new com.english_study.exception.InvalidCredentialException("Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.");
        }

        String newAccessToken = jwtUtil.generateToken(userDTO);

        return new TokenResponse(
                refreshToken,
                newAccessToken,
                userDTO
        );
    }
}