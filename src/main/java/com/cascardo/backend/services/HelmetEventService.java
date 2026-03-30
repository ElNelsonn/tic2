package com.cascardo.backend.services;

import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.mappers.HelmetEventMapper;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.repositories.HelmetEventRepository;
import com.cascardo.backend.validators.HelmetEventValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class HelmetEventService {

    private final HelmetEventRepository helmetEventRepository;
    private final HelmetEventValidator helmetEventValidator;

    @Transactional
    public void createHelmetEvent(CreateHelmetEventDto createHelmetEventDto, MultipartFile photo) {

        if (createHelmetEventDto.parentEventId() != null) {
            helmetEventValidator.validateParentEventExists(createHelmetEventDto.parentEventId());
        }



    }



}
