package com.procureflow.procureflowbackend.auth.service;

import com.procureflow.procureflowbackend.auth.dto.LoginRequest;
import com.procureflow.procureflowbackend.auth.dto.LoginResponse;
import com.procureflow.procureflowbackend.auth.dto.LogoutRequest;
import com.procureflow.procureflowbackend.auth.dto.RefreshTokenRequest;
import com.procureflow.procureflowbackend.auth.entity.RefreshToken;
import com.procureflow.procureflowbackend.auth.entity.User;
import com.procureflow.procureflowbackend.auth.entity.UserStatus;
import com.procureflow.procureflowbackend.auth.repository.RefreshTokenRepository;
import com.procureflow.procureflowbackend.auth.repository.UserRepository;
import com.procureflow.procureflowbackend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmailAndIsDeletedFalse(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BadCredentialsException("User account is not active");
        }

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        );

        if (!matches) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(
                user.getUserId(),
                user.getEmail()
        );

        String refreshTokenValue = jwtService.generateRefreshToken(
                user.getUserId(),
                user.getEmail()
        );

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshTokenId(UUID.randomUUID())
                .userId(user.getUserId())
                .token(refreshTokenValue)
                .expiresAt(LocalDateTime.now().plusDays(7))
                .createdAt(LocalDateTime.now())
                .isRevoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);

        user.setLastLoginAt(LocalDateTime.now());

        userRepository.save(user);

        return LoginResponse.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshTokenValue)
                .tokenType("Bearer")
                .expiresIn(900L)
                .build();
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenAndIsRevokedFalse(request.getRefreshToken())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {

            refreshToken.setIsRevoked(true);
            refreshToken.setRevokedAt(LocalDateTime.now());

            refreshTokenRepository.save(refreshToken);

            throw new BadCredentialsException("Refresh token expired");
        }

        User user = userRepository.findById(refreshToken.getUserId())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(
                user.getUserId(),
                user.getEmail()
        );

        return LoginResponse.builder()
                .userId(user.getUserId())
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(900L)
                .build();
    }

    public void logout(LogoutRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenAndIsRevokedFalse(request.getRefreshToken())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid refresh token"));

        refreshToken.setIsRevoked(true);
        refreshToken.setRevokedAt(LocalDateTime.now());

        refreshTokenRepository.save(refreshToken);
    }
}
