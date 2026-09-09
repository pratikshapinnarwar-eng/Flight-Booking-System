package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Flattened for the frontend. The entity holds nested Airline and Airport
 * objects; this pulls out just the fields the UI needs, plus a duration the
 * entity does not have at all.
 */
@Data
@AllArgsConstructor
public class FlightResponse {

    private Integer flightId;
    private String flightNumber;

    private Integer airlineId;
    private String airlineName;
    private String airlineCode;

    private String aircraftModel;

    private String departureAirportCode;
    private String departureCity;
    private String arrivalAirportCode;
    private String arrivalCity;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String duration;

    private BigDecimal basePrice;
    private String status;
}
