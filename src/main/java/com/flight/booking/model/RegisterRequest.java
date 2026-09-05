package com.flight.booking.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * What the client SENDS to register.
 *
 * No userId, no role, no createdAt - the client must not be able to set
 * those. The server decides them.
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String phone;
}

