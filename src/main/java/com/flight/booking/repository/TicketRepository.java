package com.flight.booking.repository;

import com.flight.booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByBooking_BookingId(Integer bookingId);
}
