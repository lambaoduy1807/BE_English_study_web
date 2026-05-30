package com.english_study.controller;

import com.english_study.config.cloudiary.CloudinaryService;
import com.english_study.model.request.LoginRequest;
import com.english_study.model.request.UpdateProfileRequest;
import com.english_study.model.response.ApiResponse;
import com.english_study.sercurity.SecurityUtil;
import com.english_study.service.UploadService;
import com.english_study.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    UserService userService;
    UploadService uploadService;
    CloudinaryService cloudinaryService;

    @PostMapping
    public ApiResponse update(@RequestBody UpdateProfileRequest updateRequest) {
        String userID=SecurityUtil.getCurrentUserId();
        return ApiResponse.success( userService.updateUser(userID,updateRequest),"update success");
    }

    @PostMapping("/img")
    public ApiResponse updateImage(@RequestParam("image") MultipartFile image) throws IOException {
        String userID = SecurityUtil.getCurrentUserId();
        
        uploadService.validateImage(image);
        
        String url = cloudinaryService.upload(image);
        userService.updateAvatar(userID, url);
        
        return ApiResponse.success(url, "Cập nhật ảnh đại diện thành công");
    }
}
