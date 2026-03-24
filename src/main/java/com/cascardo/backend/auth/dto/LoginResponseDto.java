package com.cascardo.backend.auth.dto;

import java.time.LocalDateTime;

public record LoginResponseDto(

        String email,
        String jwtToken,
        LocalDateTime timestamp,
        String ip

) {
}
