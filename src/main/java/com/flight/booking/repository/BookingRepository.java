package com.flight.booking.repository;

import com.flight.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByUser_UserIdOrderByBookingDateDesc(Integer userId);
}
