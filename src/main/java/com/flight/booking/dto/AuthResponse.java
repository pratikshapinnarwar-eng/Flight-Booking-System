package com.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * What we SEND BACK after register or login.
 *
 * There is deliberately no password field. The User entity holds the password
 * hash, and it must never leave the server - that is the single most important
 * reason DTOs exist.
 */
@Data
@AllArgsConstructor
public class AuthResponse {

    private Integer userId;
    private String userName;
    private String email;
    private String role;
    private String message;
}
