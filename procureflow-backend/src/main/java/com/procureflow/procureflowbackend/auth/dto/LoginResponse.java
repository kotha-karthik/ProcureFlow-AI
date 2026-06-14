package com.procureflow.procureflowbackend.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LoginResponse {

    private UUID userId;

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;
}
