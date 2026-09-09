package com.flight.booking.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** JSON in: {"airline_name": "IndiGo", "airline_code": "6E"} */
@Data
public class AirlineRequest {

    @NotBlank(message = "Airline name is required")
    @Size(max = 100, message = "Airline name must be at most 100 characters")
    private String airlineName;

    @NotBlank(message = "Airline code is required")
    @Size(min = 2, max = 10, message = "Airline code must be 2 to 10 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Airline code must be uppercase letters and digits")
    private String airlineCode;
}
