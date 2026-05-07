package com.english_study.model.response;


import com.english_study.model.dto.UserDTO;

public record TokenResponse(
    String refreshToken,
    String accessToken,
    UserDTO user
){
    public static <T> TokenResponse expired(){
        return new TokenResponse(null,null,null);
    }
}
