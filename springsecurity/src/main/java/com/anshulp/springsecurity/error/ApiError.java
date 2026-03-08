package com.anshulp.springsecurity.error;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiError {
    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("status")
    private String status;

    public ApiError(String message, LocalDateTime timestamp, String uri, String status) {
        this.message = message;
        this.timestamp = timestamp;
        this.uri = uri;
        this.status = status;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUri() {
        return uri;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
