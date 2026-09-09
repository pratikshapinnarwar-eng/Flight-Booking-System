package com.flight.booking.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/** JSON in: {"airline_id": 1, "model": "Airbus A320"} */
@Data
public class AircraftRequest {

    @NotNull(message = "Airline id is required")
    private Integer airlineId;

    @NotBlank(message = "Model is required")
    @Size(max = 80, message = "Model must be at most 80 characters")
    private String model;
}
