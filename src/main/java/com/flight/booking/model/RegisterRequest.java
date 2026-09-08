package com.flight.booking.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email")
    @Size(max = 120, message = "Email must be at most 120 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone must be a valid 10-digit Indian mobile number")
    private String phone;
}
