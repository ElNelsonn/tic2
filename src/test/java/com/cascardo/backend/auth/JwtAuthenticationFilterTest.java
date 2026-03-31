package com.cascardo.backend.auth.jwt;

import com.cascardo.backend.auth.dto.CustomUserDetailsDto;
import com.cascardo.backend.auth.user.details.CustomUserDetails;
import com.cascardo.backend.auth.user.details.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock private JwtService jwtService;
    @Mock private CustomUserDetailsService userDetailsService;
    @Mock private HandlerExceptionResolver resolver;

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilter_noBearer_callsChainOnly() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, resolver);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getHeader("Authorization")).thenReturn(null);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verifyNoInteractions(jwtService, userDetailsService);
    }

    @Test
    void doFilter_validBearer_setsAuthentication() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, userDetailsService, resolver);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse res = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(req.getHeader("Authorization")).thenReturn("Bearer abc");
        CustomUserDetailsDto dto = CustomUserDetailsDto.builder().id(1L).email("u@mail.com").name("U").lastName("L").build();
        CustomUserDetails cud = CustomUserDetails.builder().id(1L).email("u@mail.com").name("U").lastName("L").hashedPassword("x").build();

        when(jwtService.getUserDetails("abc")).thenReturn(dto);
        when(userDetailsService.loadUser(dto)).thenReturn(cud);

        filter.doFilter(req, res, chain);

        verify(chain).doFilter(req, res);
        verify(jwtService).getUserDetails("abc");
        verify(userDetailsService).loadUser(dto);
    }
}