package com.cascardo.backend.validators;


import com.cascardo.backend.exceptions.DateTimeConflictException;
import com.cascardo.backend.repositories.DataRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataValidator {

    private final DataRepository dataRepository;


    public void validateDateTimeIsNotInUse(LocalDateTime dateTime) throws DateTimeConflictException {

        if (dataRepository.existsByDateTime(dateTime)) {
            throw new DateTimeConflictException("La fecha y hora ya se encuentra registrada.");
        }
    }




}
