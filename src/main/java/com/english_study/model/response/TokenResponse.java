package com.english_study.model.response;


import com.english_study.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    String refreshToken;
    String accessToken;
    UserDTO user;
}
