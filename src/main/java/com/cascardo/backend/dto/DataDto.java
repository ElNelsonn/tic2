package com.cascardo.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DataDto(

        @NotNull(message = "La fecha no puede ser nula.")
        LocalDateTime dateTime,

        Float temperature,

        @Positive(message = "La humedad debe ser mayor a 0.")
        Float humidity

) {
}
