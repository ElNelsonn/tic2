package com.cascardo.backend.auth.dto;


import lombok.Builder;
import org.springframework.security.authentication.BadCredentialsException;

@Builder
public record LoginRequestDto(

        String email,
        String password,
        Boolean rememberMe

) {
    public LoginRequestDto {

        if (email == null || password == null) {
            throw new BadCredentialsException("Email o contraseña incorrectos.");
        }
    }
}
