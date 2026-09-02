package com.flight.booking.service;

import com.flight.booking.model.AirlineRequest;
import com.flight.booking.model.AirlineResponse;

import java.util.List;

/**
 * The contract. Controllers depend on this interface, never on the class that
 * implements it. That keeps the controller easy to test and the implementation
 * easy to swap.
 */
public interface AirlineService {

    AirlineResponse create(AirlineRequest request);

    List<AirlineResponse> getAll();

    AirlineResponse getById(Integer id);

    AirlineResponse getByCode(String code);

    AirlineResponse update(Integer id, AirlineRequest request);

    void delete(Integer id);
}
