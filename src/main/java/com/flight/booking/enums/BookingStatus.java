package com.flight.booking.enums;

/**
 * A booking starts PENDING with the seats held, becomes CONFIRMED once
 * payment succeeds, or CANCELLED if the user cancels or payment fails.
 */
public enum BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED
}
