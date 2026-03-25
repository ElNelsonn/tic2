package com.cascardo.backend.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
public record HalmetEventDto(
    Long id,
    String eventType,
    LocalDateTime dateTime,
    Integer duration,
    String imageUrl,
    Long parentEventId,
    List<HalmetEventDto> childEvents,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}