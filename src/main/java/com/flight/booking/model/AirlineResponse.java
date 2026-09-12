package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/** JSON out: {"airline_id": 1, "airline_name": "IndiGo", "airline_code": "6E"} */
@Data
@AllArgsConstructor
public class AirlineResponse {
    private Integer airlineId;
    private String airlineName;
    private String airlineCode;
}
