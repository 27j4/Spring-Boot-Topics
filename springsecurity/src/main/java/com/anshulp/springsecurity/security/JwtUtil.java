package com.anshulp.springsecurity.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/*

This class is responsible for creating and validating JWT tokens.

It reads the JWT secret and expiration time from application.properties.

The secret is stored in Base64 encoded form and decoded before use.

getSigningKey() generates the cryptographic key used to sign tokens.

generateToken() creates a JWT containing the username and expiration time.

The token is signed using the HS256 algorithm.

extractUsername() parses the JWT and retrieves the username from the payload.

isTokenValid() verifies the token signature and checks if it is expired or malformed.

If parsing fails, the method catches JwtException and returns false.

This class is later used by the authentication controller and JWT filter.

*/

@Component
public class JwtUtil {

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.expiration-ms}")
    private long jwtExpiration;

    private Key signingKey;

    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }

    public String generateToken(String username) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token) {

        try {
            Jwts.parser()
                    .verifyWith((SecretKey) signingKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
