package com.procureflow.procureflowbackend.auth.controller;

import com.procureflow.procureflowbackend.auth.dto.LoginRequest;
import com.procureflow.procureflowbackend.auth.dto.LoginResponse;
import com.procureflow.procureflowbackend.auth.dto.LogoutRequest;
import com.procureflow.procureflowbackend.auth.dto.RefreshTokenRequest;
import com.procureflow.procureflowbackend.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutRequest request) {

        authService.logout(request);

        return ResponseEntity.ok("Logout successful");
    }
}
