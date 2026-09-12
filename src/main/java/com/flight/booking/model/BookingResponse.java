package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResponse {

    @JsonProperty("booking_id")             private Integer bookingId;
    @JsonProperty("user_id")                private Integer userId;
    @JsonProperty("flight_id")              private Integer flightId;
    @JsonProperty("flight_number")          private String flightNumber;
    @JsonProperty("airline_name")           private String airlineName;
    @JsonProperty("departure_airport_code") private String departureAirportCode;
    @JsonProperty("arrival_airport_code")   private String arrivalAirportCode;
    @JsonProperty("departure_time")         private LocalDateTime departureTime;
    @JsonProperty("booking_date")           private LocalDateTime bookingDate;
    @JsonProperty("total_amount")           private BigDecimal totalAmount;
    @JsonProperty("booking_status")         private String bookingStatus;
    @JsonProperty("tickets")                private List<TicketResponse> tickets;
}
