package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * @JsonProperty names each field explicitly, so the JSON is snake_case whether
 * or not the global Jackson naming strategy is set. No silent nulls.
 *
 * {
 *   "user_id": 1,
 *   "flight_id": 2,
 *   "passengers": [
 *     {"passenger_id": 1, "flight_seat_id": 10}
 *   ]
 * }
 */
@Data
public class BookingRequest {

    @JsonProperty("user_id")
    @NotNull(message = "user_id is required")
    private Integer userId;

    @JsonProperty("flight_id")
    @NotNull(message = "flight_id is required")
    private Integer flightId;

    @JsonProperty("passengers")
    @NotEmpty(message = "At least one passenger is required")
    @Size(max = 9, message = "Maximum 9 passengers per booking")
    @Valid
    private List<PassengerSeat> passengers;

    @Data
    public static class PassengerSeat {

        @JsonProperty("passenger_id")
        @NotNull(message = "passenger_id is required")
        private Integer passengerId;

        @JsonProperty("flight_seat_id")
        @NotNull(message = "flight_seat_id is required")
        private Integer flightSeatId;
    }
}
