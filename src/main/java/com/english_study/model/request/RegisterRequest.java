package com.english_study.model.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email_phoneNumber;
    private String password;
}
