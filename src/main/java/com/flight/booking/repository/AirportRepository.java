package com.flight.booking.repository;

import com.flight.booking.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    Optional<Airport> findByAirportCode(String airportCode);
    boolean existsByAirportCode(String airportCode);
    List<Airport> findByCityContainingIgnoreCase(String city);
}
