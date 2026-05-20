package com.english_study.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Id
    private String id;
    //    Thông tin cơ bản
    private String name;
    private String email;
    private String fullName;
    private boolean isDelete;
    private String avatar;
    private String roleId;
    //    dành cho chức năng streak
    private Date beginStreak;
    private Date endStreak;

    private String level;// B1, B2 ...
    private int totalXP;

    private String refreshToken;//Token để xác thực và cấp lại accessToken

    private List<String> my_vocabs;// danh sách id của các bộ từ vựng
}