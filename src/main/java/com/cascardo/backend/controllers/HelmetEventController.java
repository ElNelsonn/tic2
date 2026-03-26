package com.cascardo.backend.controllers;

import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.services.HalmetEventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/helmet-events")
@AllArgsConstructor
public class HelmetEventController {

    private final HalmetEventService halmetEventService;

    @PostMapping("")
    public ResponseEntity<HelmetEvent> createHelmetEvent(@Valid @RequestBody CreateHelmetEventDto createHelmetEventDto) {

        HelmetEvent newEvent = halmetEventService.createHelmetEvent(createHelmetEventDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }
}