package com.cascardo.backend.auth.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginResponseDto(

        String email,
        String jwtToken,
        LocalDateTime timestamp,
        String ip

) {
}
