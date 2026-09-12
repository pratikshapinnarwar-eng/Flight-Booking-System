package com.flight.booking.repository;

import com.flight.booking.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
    Optional<Airline> findByAirlineCode(String airlineCode);
    boolean existsByAirlineCode(String airlineCode);
}
