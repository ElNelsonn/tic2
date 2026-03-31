package com.cascardo.backend.services;


import com.cascardo.backend.dto.AddDataDto;
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
    public Data saveData(AddDataDto addDataDto) throws DateTimeConflictException {

        dataValidator.validateDateTimeIsNotInUse(addDataDto.dateTime());

        Data newData = DataMapper.toEntity(addDataDto);

        return dataRepository.save(newData);
    }











}
