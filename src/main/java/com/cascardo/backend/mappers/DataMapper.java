package com.cascardo.backend.mappers;

import com.cascardo.backend.dto.AddDataDto;
import com.cascardo.backend.models.Data;

public class DataMapper {


    public static Data toEntity(AddDataDto addDataDto) {

        return Data.builder().
                dateTime(addDataDto.dateTime()).
                temperature(addDataDto.temperature()).
                humidity(addDataDto.humidity()).
                build();
    }


}
