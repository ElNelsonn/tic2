package com.cascardo.backend.auth.dto;

import com.nightmap.backend.auth.Role;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;


@Builder
public record CustomUserDetailsDto (

    Long id,
    String username,
    String email,
    String hashedPassword,
    List<Role> roles
) {
    public CustomUserDetailsDto {

        if (roles == null) {
            roles = new ArrayList<>();
        }
    }

}
