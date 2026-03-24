package com.cascardo.backend.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SuccessResponse(

        String message,
        int status,
        LocalDateTime timestamp,
        String path

) {
}
