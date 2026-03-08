package com.anshulp.springsecurity.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle UserAlreadyExistsException
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "409"
        );
        return ResponseEntity.status(409).body(error);
    }

    /**
     * Handle InvalidCredentialsException
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError> handleInvalidCredentialsException(
            InvalidCredentialsException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "401"
        );
        return ResponseEntity.status(401).body(error);
    }

    /**
     * Handle UnauthorizedException
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> handleUnauthorizedException(
            UnauthorizedException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "401"
        );
        return ResponseEntity.status(401).body(error);
    }

    /**
     * Handle InvalidRequestException
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiError> handleInvalidRequestException(
            InvalidRequestException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "400"
        );
        return ResponseEntity.status(400).body(error);
    }

    /**
     * Handle Spring Security AuthenticationException (bad credentials, etc.)
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                "Invalid username or password",
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "401"
        );
        return ResponseEntity.status(401).body(error);
    }

    /**
     * Handle BadCredentialsException specifically
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(
            BadCredentialsException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                "Invalid username or password",
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "401"
        );
        return ResponseEntity.status(401).body(error);
    }

    /**
     * Handle validation exceptions (invalid request body)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("Invalid request parameters");

        ApiError error = new ApiError(
                message,
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "400"
        );
        return ResponseEntity.status(400).body(error);
    }

    /**
     * Handle all other RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                ex.getMessage(),
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "500"
        );
        return ResponseEntity.status(500).body(error);
    }

    /**
     * Handle all other generic exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                "An unexpected error occurred",
                java.time.LocalDateTime.now(),
                request.getRequestURI(),
                "500"
        );
        return ResponseEntity.status(500).body(error);
    }
}
