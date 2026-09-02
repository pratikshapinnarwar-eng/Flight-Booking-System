package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * What we SEND BACK to the client.
 *
 * It looks similar to the entity today, but keeping them separate means we can
 * rename a database column without breaking the API, add computed fields later,
 * and never accidentally leak an internal field such as a password hash.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineResponse {

    private Integer airlineId;
    private String airlineName;
    private String airlineCode;
}
