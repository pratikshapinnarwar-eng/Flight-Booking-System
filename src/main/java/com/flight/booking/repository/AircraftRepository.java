package com.flight.booking.repository;

import com.flight.booking.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {
    /** The underscore walks into the related Airline entity. */
    List<Aircraft> findByAirline_AirlineId(Integer airlineId);
}
