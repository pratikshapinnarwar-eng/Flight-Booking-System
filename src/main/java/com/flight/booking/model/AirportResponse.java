package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirportResponse {
    private Integer airportId;
    private String airportName;
    private String city;
    private String country;
    private String airportCode;
}
