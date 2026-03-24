package com.cascardo.backend.auth;

import com.nightmap.backend.auth.dto.LoginRequestDto;
import com.nightmap.backend.auth.dto.LoginResponseDto;
import com.nightmap.backend.auth.dto.RefreshRequestDto;
import com.nightmap.backend.auth.dto.RefreshResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest, HttpServletRequest request) {

        return ResponseEntity.ok(authService.authenticate(loginRequest, request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refreshToken(@Valid @RequestBody RefreshRequestDto refreshRequest, HttpServletRequest request) {

        return ResponseEntity.ok(authService.refresh(refreshRequest, request));
    }










}
