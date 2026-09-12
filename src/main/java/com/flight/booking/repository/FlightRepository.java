package com.flight.booking.repository;

import com.flight.booking.entity.Flight;
import com.flight.booking.enums.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    /**
     * The main search. Matches on airport CODE (DEL, BOM) rather than id,
     * because that is what a user types. BETWEEN covers the whole chosen day.
     */
    @Query("""
           SELECT f FROM Flight f
           WHERE upper(f.departureAirport.airportCode) = upper(:from)
             AND upper(f.arrivalAirport.airportCode)   = upper(:to)
             AND f.departureTime BETWEEN :start AND :end
             AND f.status = :status
           ORDER BY f.departureTime
           """)
    List<Flight> search(@Param("from") String from,
                        @Param("to") String to,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end,
                        @Param("status") FlightStatus status);

    boolean existsByFlightNumberAndDepartureTime(String flightNumber, LocalDateTime departureTime);
}
