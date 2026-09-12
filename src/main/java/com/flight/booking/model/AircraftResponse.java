package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Flattened: the entity holds a whole Airline, the frontend needs id + name. */
@Data
@AllArgsConstructor
public class AircraftResponse {
    private Integer aircraftId;
    private String model;
    private Integer airlineId;
    private String airlineName;
}
