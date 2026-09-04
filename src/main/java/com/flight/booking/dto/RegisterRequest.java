package com.flight.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO = Data Transfer Object. What the client SENDS to register.
 *
 * Note there is no userId, no role and no createdAt. The client must not be
 * able to set those - the server decides them. That is one of the main
 * reasons we do not accept the User entity directly.
 */
@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    private String phone;
}
