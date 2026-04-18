package com.english_study.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class JwtUserPrincipal {
    private UUID userId;
    private String email;
    private String name;
    private Long phone;
    private String role;
}