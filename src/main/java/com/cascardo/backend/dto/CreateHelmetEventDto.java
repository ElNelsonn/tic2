package com.cascardo.backend.dto;

import com.cascardo.backend.enums.EventType;
import jakarta.validation.constraints.*;

public record CreateHelmetEventDto(

    @NotNull(message = "El tipo de evento no puede ser nulo.")
    EventType type,

    @NotNull(message = "La duración no puede ser nula.")
    @Min(value = 0, message = "La duración debe ser mayor o igual a 0.")
    Integer duration,

    @Size(max = 500, message = "La URL de imagen no puede superar 500 caracteres.")
    String imageUrl,

    @Positive(message = "El ID del evento padre debe ser mayor a 0.")
    Long parentEventId

) {}