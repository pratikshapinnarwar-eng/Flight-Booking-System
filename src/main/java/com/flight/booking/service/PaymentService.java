package com.flight.booking.service;

import com.flight.booking.entity.Booking;
import com.flight.booking.entity.Payment;
import com.flight.booking.entity.Ticket;
import com.flight.booking.enums.*;
import com.flight.booking.model.PaymentRequest;
import com.flight.booking.model.PaymentResponse;
import com.flight.booking.repository.BookingRepository;
import com.flight.booking.repository.PaymentRepository;
import com.flight.booking.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    /**
     * Pay for a booking.
     *
     * SUCCESS -> booking becomes CONFIRMED.
     * FAILED  -> booking becomes CANCELLED and every seat goes back to
     *            AVAILABLE. That release is the step people forget; without it
     *            the flight silently sells out with seats nobody holds.
     *
     * All of it is one transaction, so you can never end up with a confirmed
     * booking and a failed payment, or released seats and a live ticket.
     */
    @Transactional
    public PaymentResponse pay(PaymentRequest req) {

        Booking booking = bookingRepository.findById(req.getBookingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Booking not found with id: " + req.getBookingId()));

        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Booking " + req.getBookingId() + " has already been paid for");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Booking " + req.getBookingId() + " has been cancelled and cannot be paid");
        }

        // The amount must match exactly. compareTo, not equals - equals would
        // treat 5200.0 and 5200.00 as different BigDecimals.
        if (booking.getTotalAmount().compareTo(req.getAmount()) != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Amount does not match. Expected " + booking.getTotalAmount()
                    + " but received " + req.getAmount());
        }

        boolean failed = Boolean.TRUE.equals(req.getSimulateFailure());

        Payment payment = paymentRepository.save(Payment.builder()
                .booking(booking)
                .paymentDate(LocalDateTime.now())
                .amount(req.getAmount())
                .paymentMethod(PaymentMethod.valueOf(req.getPaymentMethod()))
                .paymentStatus(failed ? PaymentStatus.FAILED : PaymentStatus.SUCCESS)
                .build());

        String message;

        if (failed) {
            // Release the seats so someone else can buy them
            List<Ticket> tickets = ticketRepository.findByBooking_BookingId(booking.getBookingId());
            for (Ticket t : tickets) {
                t.setTicketStatus(TicketStatus.CANCELLED);
                t.setCancelledAt(LocalDateTime.now());
                t.getFlightSeat().setSeatStatus(SeatStatus.AVAILABLE);
            }
            ticketRepository.saveAll(tickets);

            booking.setBookingStatus(BookingStatus.CANCELLED);
            message = "Payment failed. The booking was cancelled and the seats released.";
            log.warn("Payment failed for booking {}, {} seats released",
                    booking.getBookingId(), tickets.size());

        } else {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            message = "Payment successful. Your booking is confirmed.";
            log.info("Payment {} succeeded, booking {} confirmed",
                    payment.getPaymentId(), booking.getBookingId());
        }

        return toResponse(payment, booking, message);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> getByBooking(Integer bookingId) {
        return paymentRepository.findByBooking_BookingId(bookingId)
                .stream()
                .map(p -> toResponse(p, p.getBooking(), null))
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponse getById(Integer id) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment not found with id: " + id));
        return toResponse(p, p.getBooking(), null);
    }

    /** Refund a confirmed booking. Marks the payment REFUNDED and frees the seats. */
    @Transactional
    public PaymentResponse refund(Integer paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Payment not found with id: " + paymentId));

        if (payment.getPaymentStatus() != PaymentStatus.SUCCESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Only a successful payment can be refunded");
        }

        Booking booking = payment.getBooking();

        List<Ticket> tickets = ticketRepository.findByBooking_BookingId(booking.getBookingId());
        for (Ticket t : tickets) {
            t.setTicketStatus(TicketStatus.CANCELLED);
            t.setCancelledAt(LocalDateTime.now());
            t.getFlightSeat().setSeatStatus(SeatStatus.AVAILABLE);
        }
        ticketRepository.saveAll(tickets);

        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        booking.setBookingStatus(BookingStatus.CANCELLED);

        log.info("Payment {} refunded, {} seats released", paymentId, tickets.size());

        return toResponse(payment, booking, "Refund processed. The seats have been released.");
    }

    private PaymentResponse toResponse(Payment p, Booking b, String message) {
        return new PaymentResponse(
                p.getPaymentId(),
                b.getBookingId(),
                p.getAmount(),
                p.getPaymentMethod().name(),
                p.getPaymentStatus().name(),
                p.getPaymentDate(),
                b.getBookingStatus().name(),
                message);
    }
}
