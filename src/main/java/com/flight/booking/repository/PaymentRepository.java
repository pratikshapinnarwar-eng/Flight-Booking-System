package com.flight.booking.repository;

import com.flight.booking.entity.Payment;
import com.flight.booking.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByBooking_BookingId(Integer bookingId);

    boolean existsByBooking_BookingIdAndPaymentStatus(Integer bookingId, PaymentStatus status);
}
