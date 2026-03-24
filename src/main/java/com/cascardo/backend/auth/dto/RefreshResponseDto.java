package com.cascardo.backend.auth.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RefreshResponseDto(

        String email,
        String jwtAccessToken,
        String jwtRefreshToken,
        LocalDateTime timestamp,
        String ip

) {
}
