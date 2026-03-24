package com.cascardo.backend.dto;

import com.nightmap.backend.auth.Role;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Builder
public record UserDto (

    String name,

    String lastName,

    String email,

) {
}
