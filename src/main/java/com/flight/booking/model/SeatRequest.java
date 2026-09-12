package com.flight.booking.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SeatRequest {

    @NotNull(message = "Aircraft id is required")
    private Integer aircraftId;

    @NotBlank(message = "Seat number is required")
    @Pattern(regexp = "^[0-9]{1,3}[A-F]$", message = "Seat number must look like 12A")
    private String seatNumber;

    @NotBlank(message = "Seat class is required")
    @Pattern(regexp = "ECONOMY|BUSINESS|FIRST", message = "Seat class must be ECONOMY, BUSINESS or FIRST")
    private String seatClass;

    @NotBlank(message = "Seat type is required")
    @Pattern(regexp = "WINDOW|MIDDLE|AISLE", message = "Seat type must be WINDOW, MIDDLE or AISLE")
    private String seatType;
}
