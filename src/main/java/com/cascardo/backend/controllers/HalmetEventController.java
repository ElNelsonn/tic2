package com.cascardo.backend.controllers;

import com.cascardo.backend.dto.CreateHalmetEventDto;
import com.cascardo.backend.models.HalmetEvent;
import com.cascardo.backend.services.HalmetEventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/halmet-events")
@AllArgsConstructor
public class HalmetEventController {

    private final HalmetEventService halmetEventService;

    @PostMapping
    public ResponseEntity<HalmetEvent> createHalmetEvent(@Valid @RequestBody CreateHalmetEventDto createHalmetEventDto) {
        HalmetEvent newEvent = halmetEventService.createHalmetEvent(createHalmetEventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }
}