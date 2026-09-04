package com.flight.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * What we send back after register or login.
 * No password field on purpose - the hash must never leave the server.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private Integer userId;
    private String userName;
    private String email;
    private String phone;
    private String message;
}
