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
    //    Thông tin cơ bản
    private String name;
    private String password;
    private String email;
    private String fullName;
    private boolean isDelete;
    private String avatar;
    private String roleId;
    //    dành cho chức năng streak
    private Date beginStreak;
    private Date endStreak;
    private String rank;
    private String level;// B1, B2 ...
    private int totalXP;

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


