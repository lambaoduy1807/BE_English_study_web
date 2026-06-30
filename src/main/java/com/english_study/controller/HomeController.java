package com.english_study.controller;

import com.english_study.model.dto.DashboardResponseDTO;
import com.english_study.service.DashboardService;
import lombok.RequiredArgsConstructor;
import com.english_study.model.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ApiResponse getDashboard(org.springframework.security.core.Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.error(401, "Unauthorized: Please login.");
        }

        com.english_study.model.JwtUserPrincipal principal = (com.english_study.model.JwtUserPrincipal) authentication.getPrincipal();
        String userId = principal.getUserId();
        DashboardResponseDTO data = dashboardService.getDashboardData(userId);
        return ApiResponse.success(data, "Lấy dữ liệu dashboard thành công");
    }
}
