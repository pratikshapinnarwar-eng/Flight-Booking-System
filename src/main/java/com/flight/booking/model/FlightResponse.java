package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlightResponse {

    private Integer flightId;
    private String flightNumber;

    private Integer airlineId;
    private String airlineName;
    private String airlineCode;

    private Integer aircraftId;
    private String aircraftModel;

    private Integer departureAirportId;
    private String departureAirportCode;
    private String departureCity;

    private Integer arrivalAirportId;
    private String arrivalAirportCode;
    private String arrivalCity;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String duration;

    private BigDecimal basePrice;
    private String status;
}
