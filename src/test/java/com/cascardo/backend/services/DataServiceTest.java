package com.cascardo.backend.services;

import com.cascardo.backend.dto.AddDataDto;
import com.cascardo.backend.models.Data;
import com.cascardo.backend.repositories.DataRepository;
import com.cascardo.backend.validators.DataValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    @Mock private DataRepository dataRepository;
    @Mock private DataValidator dataValidator;
    @InjectMocks private DataService dataService;

    @Test
    void saveData_ok() {
        AddDataDto dto = new AddDataDto(LocalDateTime.now(), 21.5f, 40.0f);
        Data saved = Data.builder().id(10L).dateTime(dto.dateTime()).temperature(dto.temperature()).humidity(dto.humidity()).build();

        when(dataRepository.save(any(Data.class))).thenReturn(saved);

        Data result = dataService.saveData(dto);

        verify(dataValidator).validateDateTimeIsNotInUse(dto.dateTime());
        verify(dataRepository).save(any(Data.class));
        assertEquals(10L, result.getId());
    }
}