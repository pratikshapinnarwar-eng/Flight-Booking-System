package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * One seat on one flight, with its price for that class.
 * This is what the seat-selection screen renders.
 */
@Data
@AllArgsConstructor
public class FlightSeatResponse {
    private Integer flightSeatId;
    private Integer flightId;
    private String seatNumber;
    private String seatClass;
    private String seatType;
    private String seatStatus;
    private BigDecimal fare;
}
