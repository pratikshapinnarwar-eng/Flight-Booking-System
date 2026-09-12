package com.flight.booking.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Creates a whole seat map in one call instead of 180 separate POSTs.
 *
 * rows = 30 gives rows 1 to 30. Columns are always A to F.
 * The first businessRows rows are BUSINESS, the rest ECONOMY.
 * A and F are WINDOW, B and E are MIDDLE, C and D are AISLE.
 */
@Data
public class GenerateSeatsRequest {

    @NotNull(message = "Aircraft id is required")
    private Integer aircraftId;

    @NotNull(message = "Rows is required")
    @Min(value = 1, message = "Rows must be at least 1")
    @Max(value = 60, message = "Rows must be at most 60")
    private Integer rows;

    @Min(value = 0, message = "Business rows cannot be negative")
    private Integer businessRows = 0;
}
