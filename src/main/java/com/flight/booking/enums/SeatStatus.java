package com.flight.booking.enums;

/**
 * Availability of a seat ON ONE FLIGHT.
 * The same physical seat can be BOOKED on Monday and AVAILABLE on Tuesday.
 */
public enum SeatStatus {
    AVAILABLE,
    BOOKED,
    BLOCKED
}
