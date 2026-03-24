package com.cascardo.backend.auth.jwt;


import com.cascardo.backend.auth.user.details.CustomUserDetails;
import com.cascardo.backend.auth.dto.CustomUserDetailsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey signedKey;

    @PostConstruct
    public void init() {
        try {

            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            this.signedKey = Keys.hmacShaKeyFor(keyBytes);

            System.out.println("JWT Service inicializado correctamente con clave fuerte.");

        } catch (WeakKeyException e) {
            System.err.println("ERROR DE SEGURIDAD: La clave en application.yml es demasiado corta.");
            System.err.println("Para HS512, necesitas al menos 64 caracteres (512 bits).");
            throw new RuntimeException("Configuración de JWT inválida: Clave débil.", e);

        } catch (IllegalArgumentException e) {
            System.err.println("ERROR DE JWT: Clave inválida (No es Base64 válido o está vacía).");
            throw new RuntimeException("Configuración JWT inválida.", e);
        }
    }

    private SecretKey getSignInKey() {
        return this.signedKey;
    }

    public String generateToken(CustomUserDetails userDetails) {

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .subject(userDetails.getEmail())
                .claim("id", userDetails.getId())
                .claim("name", userDetails.getUsername())
                .claim("lastname", userDetails.getLastName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(getSignInKey(), Jwts.SIG.HS512)
                .compact();
    }

    private String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get("type", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public CustomUserDetailsDto getUserDetails(String token) {

        final Claims claims = extractAllClaims(token);

        if (Objects.equals(claims.get("type", String.class), "REFRESH") ||
                !Objects.equals(claims.get("type", String.class), "ACCESS")) {

            throw new JwtException("Tipo de token invalido.");
        }

        return CustomUserDetailsDto.builder().
                email(claims.getSubject()).
                name(claims.get("name", String.class)).
                lastName(claims.get("lastName", String.class)).
                id(claims.get("id", Long.class)).
                hashedPassword(null).
                build();

    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }



}
