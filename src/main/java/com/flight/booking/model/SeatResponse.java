package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatResponse {
    private Integer seatId;
    private Integer aircraftId;
    private String seatNumber;
    private String seatClass;
    private String seatType;
}
