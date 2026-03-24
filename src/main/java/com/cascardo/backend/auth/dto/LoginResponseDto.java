package com.cascardo.backend.auth.dto;

import java.time.LocalDateTime;

public record LoginResponseDto(

        String email,
        String jwtRefreshToken,
        String jwtAccessToken,
        LocalDateTime timestamp,
        String ip

) {
}
