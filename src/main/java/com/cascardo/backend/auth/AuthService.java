package com.cascardo.backend.auth;


import com.cascardo.backend.auth.dto.LoginRequestDto;
import com.cascardo.backend.auth.dto.LoginResponseDto;
import com.cascardo.backend.auth.exceptions.SessionExpiredException;
import com.cascardo.backend.auth.jwt.JwtService;
import com.cascardo.backend.auth.user.details.CustomUserDetails;
import com.cascardo.backend.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public LoginResponseDto authenticate(LoginRequestDto request, HttpServletRequest httpRequest) {

        // 1. Autenticar
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Extraer detalles (El IP viene del request original o del auth)
        String ip = httpRequest.getRemoteAddr();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        // 3. Generar Token
        String token;
        if (request.rememberMe()) {

            token = jwtService.generateLastingToken(userDetails);

        } else {

            token = jwtService.generateFastToken(userDetails);
        }

        // 4. Devolver Record
        return new LoginResponseDto(
                userDetails.getEmail(),
                token,
                LocalDateTime.now(),
                ip
        );
    }



}
