package com.cascardo.backend.auth;

import com.cascardo.backend.auth.dto.LoginRequestDto;
import com.cascardo.backend.auth.dto.LoginResponseDto;
import com.cascardo.backend.auth.jwt.JwtService;
import com.cascardo.backend.auth.user.details.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtService jwtService;
    @InjectMocks private AuthService authService;

    @Test
    void authenticate_rememberMe_true_usesLastingToken() {
        LoginRequestDto req = new LoginRequestDto("a@mail.com", "123", true);
        HttpServletRequest http = mock(HttpServletRequest.class);
        when(http.getRemoteAddr()).thenReturn("127.0.0.1");

        CustomUserDetails principal = CustomUserDetails.builder()
                .id(1L).email("a@mail.com").name("Ana").lastName("Lopez").hashedPassword("x").build();

        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(principal);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtService.generateLastingToken(principal)).thenReturn("token-largo");

        LoginResponseDto response = authService.authenticate(req, http);

        assertEquals("a@mail.com", response.email());
        assertEquals("token-largo", response.jwtToken());
        verify(jwtService).generateLastingToken(principal);
        verify(jwtService, never()).generateFastToken(any());
    }
}