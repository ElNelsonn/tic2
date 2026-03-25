package com.cascardo.backend.mappers;

import com.cascardo.backend.dto.CreateHalmetEventDto;
import com.cascardo.backend.dto.HalmetEventDto;
import com.cascardo.backend.models.HalmetEvent;

public class HalmetEventMapper {

    public static HalmetEvent toEntity(CreateHalmetEventDto createHalmetEventDto) {
        return HalmetEvent.builder()
                .eventType(createHalmetEventDto.eventType())
                .duration(createHalmetEventDto.duration())
                .imageUrl(createHalmetEventDto.imageUrl())
                .build();
    }
}

