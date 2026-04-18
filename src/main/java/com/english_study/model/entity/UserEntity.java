package com.english_study.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Id
    private String id; // Mongo nên dùng String

    private String name;
    private String password;
    private Long phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private boolean isDelete;
    private String avatar;

    private String roleId;
    private String cartId;
    private List<String> orderIds;

    private List<String> favoriteProductIds;

    public UserEntity(String username, String email, String password, String roleId) {
        this.name = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }

    public UserEntity(String username, Long phoneNumber, String password, String roleId) {
        this.name = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
    }
}


