package com.cascardo.backend.controllers;

import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.services.HelmetEventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/helmet-events")
@AllArgsConstructor
public class HelmetEventController {

    private final HelmetEventService helmetEventService;

    @PostMapping("")
    public ResponseEntity<HelmetEvent> createHelmetEvent(@Valid @RequestBody CreateHelmetEventDto createHelmetEventDto,
                                                         MultipartFile photo) {

//        HelmetEvent newEvent = helmetEventService.createHelmetEvent(createHelmetEventDto, photo);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}