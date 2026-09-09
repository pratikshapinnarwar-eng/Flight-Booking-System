package com.flight.booking.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AirportRequest {

    @NotBlank(message = "Airport name is required")
    @Size(max = 120, message = "Airport name must be at most 120 characters")
    private String airportName;

    @NotBlank(message = "City is required")
    @Size(max = 80, message = "City must be at most 80 characters")
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 80, message = "Country must be at most 80 characters")
    private String country;

    @NotBlank(message = "Airport code is required")
    @Size(min = 3, max = 10, message = "Airport code must be 3 to 10 characters")
    @Pattern(regexp = "^[A-Z]+$", message = "Airport code must be uppercase letters only")
    private String airportCode;
}
