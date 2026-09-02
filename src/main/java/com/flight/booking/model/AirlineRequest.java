package com.flight.booking.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * What the client is allowed to SEND when creating or updating an airline.
 *
 * There is deliberately no airlineId field. The client must never choose the
 * primary key - the database assigns it.
 *
 * These annotations run automatically because the controller marks the
 * parameter @Valid. On failure Spring throws MethodArgumentNotValidException,
 * which GlobalExceptionHandler converts into a clean 400 response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineRequest {

    @NotBlank(message = "Airline name is required")
    @Size(max = 100, message = "Airline name must be at most 100 characters")
    private String airlineName;

    @NotBlank(message = "Airline code is required")
    @Size(min = 2, max = 10, message = "Airline code must be 2 to 10 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Airline code must be uppercase letters and digits only")
    private String airlineCode;
}
