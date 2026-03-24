package com.cascardo.backend.auth.refresh.token;


import com.nightmap.backend.auth.exceptions.SessionExpiredException;
import com.nightmap.backend.auth.jwt.JwtService;
import com.nightmap.backend.auth.user.details.CustomUserDetails;
import com.nightmap.backend.user.User;
import com.nightmap.backend.user.UserRepository;
import com.nightmap.backend.user.service.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserInternalService  userInternalService;
    private final JwtService jwtService;

    @Value("${jwt.expiration-refresh-ms}")
    private long EXPIRATION_TIME_REFRESH;


    @Transactional
    public String generateRefreshToken(User user, CustomUserDetails userDetails) {

        if (user.getRefreshToken() != null) {

            user.setRefreshToken(null);
        }

        String token = jwtService.generateRefreshToken(userDetails);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(token)
                .expiryDate(LocalDateTime.now().plus(Duration.ofMillis(EXPIRATION_TIME_REFRESH)))
                .build();

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return token;
    }

    @Transactional
    public String generateRefreshToken(CustomUserDetails userDetails) {
        User user = userInternalService.findById(userDetails.getId());
        return generateRefreshToken(user, userDetails);
    }

    @Transactional
    public RefreshToken getRefreshTokenEntity(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SessionExpiredException("Sesión expirada."));

    }








}
