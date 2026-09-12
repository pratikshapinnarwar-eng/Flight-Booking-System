package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TicketResponse {

    @JsonProperty("ticket_id")      private Integer ticketId;
    @JsonProperty("passenger_id")   private Integer passengerId;
    @JsonProperty("passenger_name") private String passengerName;
    @JsonProperty("seat_number")    private String seatNumber;
    @JsonProperty("seat_class")     private String seatClass;
    @JsonProperty("fare")           private BigDecimal fare;
    @JsonProperty("ticket_status")  private String ticketStatus;
    @JsonProperty("cancelled_at")   private LocalDateTime cancelledAt;
}
