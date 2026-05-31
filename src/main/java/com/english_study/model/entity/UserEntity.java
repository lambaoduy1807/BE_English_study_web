package com.english_study.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.util.List;


@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserEntity {

    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private String fullName;
    private boolean isDelete;
    private String avatar;
    private String roleId;
    private Date beginStreak;
    private Date endStreak;
    private String level;
    private int totalXP;
    private int todayXP;
    private java.util.Date lastXpUpdateDate;

    private List<java.util.Date> checkinHistory; // Lưu lịch sử điểm danh

    private String refreshToken;//Token để xác thực và cấp lại accessToken

    private List<String> my_vocabs;// danh sách id của các bộ từ vựng

    public UserEntity(String username, String email, String password, String fullName, String roleId) {
        this.name = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.roleId = roleId;
    }
}


