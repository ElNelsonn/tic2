package com.cascardo.backend.mappers;

import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.models.HelmetEvent;

public class HelmetEventMapper {

    public static HelmetEvent toEntity(CreateHelmetEventDto createHelmetEventDto) {
        return HelmetEvent.builder()
                .type(createHelmetEventDto.type())
                .duration(createHelmetEventDto.duration())
                .imageUrl(createHelmetEventDto.imageUrl())
                .build();
    }
}

