package com.cascardo.backend.auth;

import com.nightmap.backend.auth.dto.LoginRequestDto;
import com.nightmap.backend.auth.dto.LoginResponseDto;
import com.nightmap.backend.auth.dto.RefreshRequestDto;
import com.nightmap.backend.auth.dto.RefreshResponseDto;
import com.nightmap.backend.auth.exceptions.SessionExpiredException;
import com.nightmap.backend.auth.jwt.JwtService;
import com.nightmap.backend.auth.refresh.token.RefreshToken;
import com.nightmap.backend.auth.refresh.token.RefreshTokenRepository;
import com.nightmap.backend.auth.refresh.token.RefreshTokenService;
import com.nightmap.backend.auth.user.details.CustomUserDetails;
import com.nightmap.backend.user.User;
import com.nightmap.backend.user.UserRepository;
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
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResponseDto authenticate(LoginRequestDto request, HttpServletRequest httpRequest) {

        // 1. Autenticar
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Extraer detalles (El IP viene del request original o del auth)
        String ip = httpRequest.getRemoteAddr();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        // 3. Generar Token
        String refreshToken = refreshTokenService.generateRefreshToken(userDetails);
        String accessToken = jwtService.generateToken(userDetails);

        // 4. Devolver Record
        return new LoginResponseDto(
                userDetails.getEmail(),
                refreshToken,
                accessToken,
                LocalDateTime.now(),
                ip
        );
    }

    @Transactional
    public RefreshResponseDto refresh(RefreshRequestDto request, HttpServletRequest httpRequest) {

        RefreshToken refreshTokenEntity =
                refreshTokenService.getRefreshTokenEntity(request.jwtRefreshToken());

        User user = refreshTokenEntity.getUser();

        if (refreshTokenEntity.isExpired()) {

            user.setRefreshToken(null);
            userRepository.save(user);

            throw new SessionExpiredException("Sesión expirada.");
        }

        CustomUserDetails userDetails = CustomUserDetails.builder().
                id(user.getId()).
                email(user.getEmail()).
                username(user.getUsername()).
                hashedPassword(null).
                roles(user.getRoles()).
                build();

        user.setRefreshToken(null);
        userRepository.save(user);

        String newRefreshToken = refreshTokenService.generateRefreshToken(user, userDetails);
        String accessToken = jwtService.generateToken(userDetails);


        return RefreshResponseDto.builder().
                email(userDetails.getEmail()).
                jwtRefreshToken(newRefreshToken).
                jwtAccessToken(accessToken).
                timestamp(LocalDateTime.now()).
                ip(httpRequest.getRemoteAddr()).
                build();
    }

}
