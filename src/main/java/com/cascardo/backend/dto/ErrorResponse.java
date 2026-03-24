package com.cascardo.backend.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse (

        String error,
        String message,
        int status,
        LocalDateTime timestamp,
        String path

) {}
