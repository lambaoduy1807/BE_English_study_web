package com.english_study.controller;

import com.english_study.model.dto.DashboardResponseDTO;
import com.english_study.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final DashboardService dashboardService;

//    @GetMapping("/dashboard")
//    public ResponseEntity<?> getDashboard(org.springframework.security.core.Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(401).body("Unauthorized: Please login.");
//        }
//
//        com.english_study.model.JwtUserPrincipal principal = (com.english_study.model.JwtUserPrincipal) authentication.getPrincipal();
//        String userId = principal.getUserId();
////        DashboardResponseDTO data = dashboardService.getDashboardData(userId);
//
////        return ResponseEntity.ok(data);
//    }
}
