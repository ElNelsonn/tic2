package com.cascardo.backend.services;

import com.cascardo.backend.dto.AddDataDto;
import com.cascardo.backend.dto.CreateHelmetEventDto;
import com.cascardo.backend.enums.EventType;
import com.cascardo.backend.minio.MinioService;
import com.cascardo.backend.models.Data;
import com.cascardo.backend.models.HelmetEvent;
import com.cascardo.backend.repositories.HelmetEventRepository;
import com.cascardo.backend.validators.HelmetEventValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HelmetEventServiceTest {

    @Mock private HelmetEventRepository helmetEventRepository;
    @Mock private HelmetEventValidator helmetEventValidator;
    @Mock private MinioService minioService;
    @Mock private DataService dataService;
    @InjectMocks private HelmetEventService helmetEventService;

    @Test
    void createHelmetEvent_photoNull_throws() {
        CreateHelmetEventDto dto = new CreateHelmetEventDto(EventType.WARNING, 10, null, new AddDataDto(LocalDateTime.now(), 20f, 30f));
        assertThrows(IllegalArgumentException.class, () -> helmetEventService.createHelmetEvent(dto, null));
    }

    @Test
    void createHelmetEvent_ok_withoutParent() {
        AddDataDto addDataDto = new AddDataDto(LocalDateTime.now(), 20f, 30f);
        CreateHelmetEventDto dto = new CreateHelmetEventDto(EventType.WARNING, 10, null, addDataDto);
        MultipartFile photo = mock(MultipartFile.class);
        when(photo.isEmpty()).thenReturn(false);

        Data data = Data.builder().id(1L).dateTime(addDataDto.dateTime()).temperature(20f).humidity(30f).build();
        when(dataService.saveData(addDataDto)).thenReturn(data);

        HelmetEvent firstSave = HelmetEvent.builder().id(99L).type(EventType.WARNING).duration(10).data(data).build();
        HelmetEvent secondSave = HelmetEvent.builder().id(99L).type(EventType.WARNING).duration(10).data(data).image("99/photo.jpg").build();

        when(helmetEventRepository.save(any(HelmetEvent.class))).thenReturn(firstSave, secondSave);
        when(minioService.saveEventPhoto(99L, photo)).thenReturn("99/photo.jpg");

        HelmetEvent result = helmetEventService.createHelmetEvent(dto, photo);

        verify(dataService).saveData(addDataDto);
        verify(minioService).saveEventPhoto(99L, photo);
        verify(helmetEventRepository, times(2)).save(any(HelmetEvent.class));
        assertEquals("99/photo.jpg", result.getImage());
    }
}