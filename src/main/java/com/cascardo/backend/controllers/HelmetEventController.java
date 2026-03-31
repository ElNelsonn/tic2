package com.cascardo.backend.controllers;

import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.dto.SuccessResponse;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.services.HelmetEventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/helmet-events")
@AllArgsConstructor
public class HelmetEventController {

    private final HelmetEventService helmetEventService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> createHelmetEvent(
            @RequestPart("data")@Valid CreateHelmetEventDto createHelmetEventDto,
            @RequestPart("photo") MultipartFile photo,
            HttpServletRequest request
    ) {

        HelmetEvent newEvent = helmetEventService.createHelmetEvent(createHelmetEventDto, photo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Evento agregado con exito.")
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build()
                );
    }


}