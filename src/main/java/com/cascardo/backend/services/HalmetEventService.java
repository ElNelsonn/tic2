package com.cascardo.backend.services;

import com.cascardo.backend.dto.CreateHalmetEventDto;
import com.cascardo.backend.dto.HalmetEventDto;
import com.cascardo.backend.mappers.HalmetEventMapper;
import com.cascardo.backend.models.HalmetEvent;
import com.cascardo.backend.repositories.HalmetEventRepository;
import com.cascardo.backend.validators.HalmetEventValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HalmetEventService {

    private final HalmetEventRepository halmetEventRepository;
    private final HalmetEventValidator halmetEventValidator;

    @Transactional
    public HalmetEvent createHalmetEvent(CreateHalmetEventDto createHalmetEventDto) {
        
        if(createHalmetEventDto.parentEventId() != null){
            halmetEventValidator.validateParentEventExists(createHalmetEventDto.parentEventId());
        }
        
        HalmetEvent newEvent = HalmetEventMapper.toEntity(createHalmetEventDto);
        return halmetEventRepository.save(newEvent);
    }



}
