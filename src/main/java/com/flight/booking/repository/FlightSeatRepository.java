package com.flight.booking.repository;

import com.flight.booking.entity.FlightSeat;
import com.flight.booking.enums.SeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, Integer> {

    List<FlightSeat> findByFlight_FlightId(Integer flightId);

    List<FlightSeat> findByFlight_FlightIdAndSeatStatus(Integer flightId, SeatStatus status);

    long countByFlight_FlightIdAndSeatStatus(Integer flightId, SeatStatus status);

    long countByFlight_FlightId(Integer flightId);

    /**
     * PESSIMISTIC_WRITE locks these rows until the transaction ends.
     *
     * Without it, two users clicking the same seat at the same millisecond
     * both read AVAILABLE and both book it. With it, the second transaction
     * waits, then correctly sees BOOKED and is rejected.
     *
     * This is the single most important line in the booking flow.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT fs FROM FlightSeat fs WHERE fs.flightSeatId IN :ids")
    List<FlightSeat> lockByIds(@Param("ids") List<Integer> ids);
}
