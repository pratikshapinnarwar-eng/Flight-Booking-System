package com.flight.booking.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    @Size(max = 20, message = "Flight number must be at most 20 characters")
    private String flightNumber;

    @NotNull(message = "Airline id is required")
    private Integer airlineId;

    @NotNull(message = "Aircraft id is required")
    private Integer aircraftId;

    @NotNull(message = "Departure airport id is required")
    private Integer departureAirportId;

    @NotNull(message = "Arrival airport id is required")
    private Integer arrivalAirportId;

    @NotNull(message = "Departure time is required")
    @Future(message = "Departure time must be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Base price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base price must be greater than zero")
    private BigDecimal basePrice;

    /** Optional. Defaults to SCHEDULED when creating. */
    @Pattern(regexp = "SCHEDULED|DELAYED|CANCELLED|DEPARTED|ARRIVED",
             message = "Invalid flight status")
    private String status;
}
