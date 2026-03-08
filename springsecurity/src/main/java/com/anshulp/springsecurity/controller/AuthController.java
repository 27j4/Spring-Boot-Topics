package com.anshulp.springsecurity.controller;

import com.anshulp.springsecurity.dto.LoginRequest;
import com.anshulp.springsecurity.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        /*

            Receives username and password
            Loads user from database
            Verifies password using BCrypt
            Creates authenticated user object
            Throws exception if authentication fails

        */

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getIdentifier(),
                                request.getPassword()
                        )
                );

        String username = authentication.getName();
        return jwtUtil.generateToken(username);
    }
}