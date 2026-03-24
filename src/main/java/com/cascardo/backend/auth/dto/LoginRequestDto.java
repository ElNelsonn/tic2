package com.cascardo.backend.auth.dto;


import org.springframework.security.authentication.BadCredentialsException;

public record LoginRequestDto(

        String email,

        String password

) {
    public LoginRequestDto {

        if (email == null || password == null) {
            throw new BadCredentialsException("Email o contraseña incorrectos.");
        }
    }
}
