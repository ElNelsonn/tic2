package com.cascardo.backend.mappers;

import com.cascardo.backend.dto.CreateUserDto;
import com.cascardo.backend.models.User;

public class UserMapper {


    public static User toEntity(CreateUserDto createUserDto, String hashedPassword) {

        return User.builder().
                email(createUserDto.email()).
                name(createUserDto.name()).
                lastName(createUserDto.lastName()).
                hashedPassword(hashedPassword).
                build();
    }
}
