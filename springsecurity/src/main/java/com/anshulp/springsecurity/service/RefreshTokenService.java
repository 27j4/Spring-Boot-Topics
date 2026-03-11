package com.anshulp.springsecurity.service;

import com.anshulp.springsecurity.entity.RefreshToken;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.error.ResourceNotFoundException;
import com.anshulp.springsecurity.error.TokenExpiredException;
import com.anshulp.springsecurity.repository.RefreshTokenRepository;
import com.anshulp.springsecurity.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${security.refresh-token.expiration-ms:86400000}")
    private long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public void verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {

            refreshTokenRepository.delete(token);

            throw new TokenExpiredException("Refresh token expired. Please login again");
        }

    }

    public RefreshToken findByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found"));
    }

    public void deleteByToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(refreshTokenRepository::delete);
    }
}