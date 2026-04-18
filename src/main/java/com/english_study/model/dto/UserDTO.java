package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;

    private String name;

    private String email;

    private Long phoneNumber;

    private String firstName;

    private String lastName;

    private String address;

    private String roleName;        // lấy từ role.getName()

    private List<String> orderIds;    // từ list orders

    private List<String> favoriteProductIds; // list favorite products

}