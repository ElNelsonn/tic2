package com.cascardo.backend.auth.dto;


import lombok.Builder;



@Builder
public record CustomUserDetailsDto (

    Long id,
    String name,
    String lastName,
    String email,
    String hashedPassword

) {
}
