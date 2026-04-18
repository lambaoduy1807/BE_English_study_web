package com.english_study.controller;


import com.english_study.model.dto.UserDTO;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.RegisterRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping("login")
    public ApiResponse login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.success(userService.login(loginRequest), "Login successful");

    }

    @PostMapping("register")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest) {
        return ApiResponse.success(userService.register(registerRequest), "Register successful");
    }

}
