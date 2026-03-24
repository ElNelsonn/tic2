package com.cascardo.backend.auth;

import com.cascardo.backend.auth.exceptions.SessionExpiredException;
import com.cascardo.backend.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice(basePackages = "com.nightmap.backend.auth")
public class AuthHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJtw(ExpiredJwtException ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("TOKEN_EXPIRED")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("El token a expirado.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJtw(MalformedJwtException ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("TOKEN_MALFORMED")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Token malformado.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleManipulatedJtw(SignatureException ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("TOKEN_ALTERED")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Token manipulado.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }


    @ExceptionHandler(SessionExpiredException.class)
    public ResponseEntity<ErrorResponse> handleExpiredSession(SessionExpiredException ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("SESSION_EXPIRED")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Tu sesión ha expirado. Por favor, vuelve a iniciar sesión.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("BAD_CREDENTIALS")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Email o contraseña incorrectos.")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthError(AuthenticationException ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("BAD_CREDENTIALS")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Email o contraseña incorrectos.")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleJwtException(Exception ex, HttpServletRequest request) {

        ErrorResponse body = ErrorResponse.builder()
                .error("AUTH_ERROR")
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Hubo un error al autenticar.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }


}
