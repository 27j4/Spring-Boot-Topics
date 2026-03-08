package com.anshulp.springsecurity.service;

import com.anshulp.springsecurity.dto.AuthResponse;
import com.anshulp.springsecurity.dto.LoginRequest;
import com.anshulp.springsecurity.dto.RegisterRequest;
import com.anshulp.springsecurity.entity.RefreshToken;
import com.anshulp.springsecurity.entity.Role;
import com.anshulp.springsecurity.entity.User;
import com.anshulp.springsecurity.error.UserAlreadyExistsException;
import com.anshulp.springsecurity.repository.UserRepository;
import com.anshulp.springsecurity.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getIdentifier(),
                                request.getPassword()
                        )
                );

        String username = authentication.getName();

        User user = userRepository
                .findByUsernameOrEmail(username, username)
                .orElseThrow();

        String accessToken = jwtUtil.generateToken(username);

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {

        RefreshToken token = refreshTokenService.findByToken(refreshToken);

        refreshTokenService.verifyExpiration(token);

        User user = token.getUser();

        // delete old refresh token
        refreshTokenService.deleteByToken(refreshToken);

        // create new refresh token
        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user.getId());

        String accessToken = jwtUtil.generateToken(user.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }

    public void logout(String refreshToken) {
        refreshTokenService.deleteByToken(refreshToken);
    }
}