package com.anshulp.springsecurity.controller;

import com.anshulp.springsecurity.dto.AuthResponse;
import com.anshulp.springsecurity.dto.LoginRequest;
import com.anshulp.springsecurity.dto.RefreshTokenRequest;
import com.anshulp.springsecurity.dto.RegisterRequest;
import com.anshulp.springsecurity.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {

        AuthResponse authResponse = authService.login(request);

        Cookie refreshCookie = new Cookie(
                "refreshToken",
                authResponse.getRefreshToken()
        );

        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false); // true in production (HTTPS)
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(refreshCookie);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(authResponse.getAccessToken())
                        .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue("refreshToken") String refreshToken) {

        AuthResponse response = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response) {

        authService.logout(refreshToken);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }
}