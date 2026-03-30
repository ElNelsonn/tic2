package com.cascardo.backend.mappers;

import com.cascardo.backend.dto.DataDto;
import com.cascardo.backend.models.Data;

public class DataMapper {


    public static Data toEntity(DataDto dataDto) {

        return Data.builder().
                dateTime(dataDto.dateTime()).
                temperature(dataDto.temperature()).
                humidity(dataDto.humidity()).
                build();
    }


}
