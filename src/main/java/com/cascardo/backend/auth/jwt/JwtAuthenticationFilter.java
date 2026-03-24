package com.cascardo.backend.auth.jwt;


import com.cascardo.backend.auth.dto.CustomUserDetailsDto;
import com.cascardo.backend.auth.user.details.CustomUserDetails;
import com.cascardo.backend.auth.user.details.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // 1. Validar que el header no esté vacío y empiece con "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token real (sin "Bearer ")
        final String jwt = authHeader.substring(7);


        // 3. Validar y extraer todo
        CustomUserDetailsDto userDetailsDto = jwtService.getUserDetails(jwt);

        // Ahora solo verificamos si el usuario ya está en el contexto
        String email = userDetailsDto.email();

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Construimos el UserDetails
            CustomUserDetails userDetails = userDetailsService.loadUser(userDetailsDto);

            // 6. Seteamos la autenticación
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Seguir con el filtro
        filterChain.doFilter(request, response);
    }
}
