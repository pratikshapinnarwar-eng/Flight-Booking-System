package com.flight.booking.repository;

import com.flight.booking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {

    /** The underscore walks into the related Aircraft entity. */
    List<Seat> findByAircraft_AircraftId(Integer aircraftId);

    boolean existsByAircraft_AircraftIdAndSeatNumber(Integer aircraftId, String seatNumber);

    long countByAircraft_AircraftId(Integer aircraftId);
}
