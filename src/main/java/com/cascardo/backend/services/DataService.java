package com.cascardo.backend.services;


import com.cascardo.backend.dto.DataDto;
import com.cascardo.backend.exceptions.DateTimeConflictException;
import com.cascardo.backend.mappers.DataMapper;
import com.cascardo.backend.models.Data;
import com.cascardo.backend.repositories.DataRepository;
import com.cascardo.backend.validators.DataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;
    private final DataValidator dataValidator;


    @Transactional
    public void saveData(DataDto dataDto) throws DateTimeConflictException {

        dataValidator.validateDateTimeIsNotInUse(dataDto.dateTime());

        Data newData = DataMapper.toEntity(dataDto);

        dataRepository.save(newData);
    }











}
