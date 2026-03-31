package com.cascardo.backend.controllers;

import com.cascardo.backend.dto.AddDataDto;
import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.enums.EventType;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.services.HelmetEventService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HelmetEventControllerTest {

    @Test
    void createHelmetEvent_returnsCreated() {
        HelmetEventService service = mock(HelmetEventService.class);
        HelmetEventController controller = new HelmetEventController(service);

        MultipartFile photo = mock(MultipartFile.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/helmet-events");

        CreateHelmetEventDto dto = new CreateHelmetEventDto(
                EventType.WARNING, 12, null, new AddDataDto(LocalDateTime.now(), 20f, 35f)
        );

        when(service.createHelmetEvent(dto, photo)).thenReturn(HelmetEvent.builder().id(1L).build());

        var response = controller.createHelmetEvent(dto, photo, request);

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("/api/helmet-events", response.getBody().path());
        verify(service).createHelmetEvent(dto, photo);
    }
}