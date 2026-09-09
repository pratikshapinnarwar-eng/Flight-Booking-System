package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TicketResponse {
    private Integer ticketId;
    private Integer passengerId;
    private String passengerName;
    private String seatNumber;
    private String seatClass;
    private BigDecimal fare;
    private String ticketStatus;
    private LocalDateTime cancelledAt;
}
