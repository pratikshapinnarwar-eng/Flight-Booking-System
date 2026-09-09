package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResponse {

    private Integer bookingId;
    private Integer userId;

    private Integer flightId;
    private String flightNumber;
    private String airlineName;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private LocalDateTime departureTime;

    private LocalDateTime bookingDate;
    private BigDecimal totalAmount;
    private String bookingStatus;

    private List<TicketResponse> tickets;
}
