package com.english_study.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private boolean isDelete;
    private String avatar;
    private String roleId;
    private Date beginStreak;
    private Date endStreak;
    private String rank;
    private String level;
    private int totalXP;
    private String refreshToken;
    private List<String> myVocabs;
    private String authProvider = "LOCAL";
    private boolean isEmailVerified = false;
    private String verificationToken;
    private String resetPasswordToken;
    private Date resetPasswordTokenExpiryDate;

    public UserEntity(String username, String email, String password, String fullName, String roleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.roleId = roleId;
    }
}
