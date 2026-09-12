package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FlightSeatResponse {

    @JsonProperty("flight_seat_id") private Integer flightSeatId;
    @JsonProperty("flight_id")      private Integer flightId;
    @JsonProperty("seat_number")    private String seatNumber;
    @JsonProperty("seat_class")     private String seatClass;
    @JsonProperty("seat_type")      private String seatType;
    @JsonProperty("seat_status")    private String seatStatus;
    @JsonProperty("fare")           private BigDecimal fare;
}
