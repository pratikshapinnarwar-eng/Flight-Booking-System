package com.flight.booking.repository;

import com.flight.booking.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA writes the implementation of this interface at startup.
 *
 * JpaRepository already gives us: save, findById, findAll, deleteById, count.
 * Extra queries are derived from the METHOD NAME:
 *   findByAirlineCode   -> SELECT * FROM airline WHERE airline_code = ?
 *   existsByAirlineCode -> SELECT COUNT(*) > 0 FROM airline WHERE airline_code = ?
 */
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

    Optional<Airline> findByAirlineCode(String airlineCode);

    boolean existsByAirlineCode(String airlineCode);

    /** Used on update: allow keeping your own code, block stealing someone else's. */
    boolean existsByAirlineCodeAndAirlineIdNot(String airlineCode, Integer airlineId);
}
