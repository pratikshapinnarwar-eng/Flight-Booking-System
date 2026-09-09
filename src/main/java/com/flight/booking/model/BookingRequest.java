package com.flight.booking.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * One booking, many passengers. Each passenger gets one seat.
 *
 * {
 *   "user_id": 1,
 *   "flight_id": 3,
 *   "passengers": [
 *     {"passenger_id": 1, "flight_seat_id": 10},
 *     {"passenger_id": 2, "flight_seat_id": 11}
 *   ]
 * }
 */
@Data
public class BookingRequest {

    @NotNull(message = "User id is required")
    private Integer userId;

    @NotNull(message = "Flight id is required")
    private Integer flightId;

    @NotEmpty(message = "At least one passenger is required")
    @Size(max = 9, message = "Maximum 9 passengers per booking")
    @Valid
    private List<PassengerSeat> passengers;

    @Data
    public static class PassengerSeat {

        @NotNull(message = "Passenger id is required")
        private Integer passengerId;

        @NotNull(message = "Flight seat id is required")
        private Integer flightSeatId;
    }
}
