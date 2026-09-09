package com.flight.booking.service;

import com.flight.booking.entity.*;
import com.flight.booking.enums.BookingStatus;
import com.flight.booking.enums.FlightStatus;
import com.flight.booking.enums.SeatStatus;
import com.flight.booking.enums.TicketStatus;
import com.flight.booking.model.BookingRequest;
import com.flight.booking.model.BookingResponse;
import com.flight.booking.model.TicketResponse;
import com.flight.booking.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The heart of the project. Everything else is CRUD; this is the real logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    /**
     * Creates a booking with one ticket per passenger.
     *
     * Everything happens in ONE transaction. If any step fails, nothing is
     * written - no half-booked state where seats are held but no ticket exists.
     */
    @Transactional
    public BookingResponse create(BookingRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with id: " + req.getUserId()));

        Flight flight = flightRepository.findById(req.getFlightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Flight not found with id: " + req.getFlightId()));

        if (flight.getStatus() == FlightStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This flight has been cancelled");
        }
        if (flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This flight has already departed");
        }

        List<Integer> seatIds = req.getPassengers().stream()
                .map(BookingRequest.PassengerSeat::getFlightSeatId).toList();

        if (seatIds.size() != new HashSet<>(seatIds).size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The same seat was selected more than once");
        }

        List<Integer> passengerIds = req.getPassengers().stream()
                .map(BookingRequest.PassengerSeat::getPassengerId).toList();
        if (passengerIds.size() != new HashSet<>(passengerIds).size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The same passenger was selected more than once");
        }

        // LOCK the seat rows. Until this transaction ends, no other request can
        // read or change them. This is what prevents two users booking the same
        // seat at the same moment.
        List<FlightSeat> seats = flightSeatRepository.lockByIds(seatIds);

        if (seats.size() != seatIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "One or more selected seats do not exist");
        }

        Map<Integer, FlightSeat> seatMap = new HashMap<>();
        for (FlightSeat fs : seats) {
            if (!fs.getFlight().getFlightId().equals(flight.getFlightId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Seat " + fs.getSeat().getSeatNumber()
                        + " does not belong to this flight");
            }
            if (fs.getSeatStatus() != SeatStatus.AVAILABLE) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Seat " + fs.getSeat().getSeatNumber() + " is no longer available");
            }
            seatMap.put(fs.getFlightSeatId(), fs);
        }

        Booking booking = bookingRepository.save(Booking.builder()
                .user(user)
                .flight(flight)
                .bookingDate(LocalDateTime.now())
                .totalAmount(BigDecimal.ZERO)
                .bookingStatus(BookingStatus.PENDING)   // CONFIRMED only after payment
                .build());

        BigDecimal total = BigDecimal.ZERO;
        List<Ticket> tickets = new ArrayList<>();

        for (BookingRequest.PassengerSeat ps : req.getPassengers()) {

            Passenger passenger = passengerRepository.findById(ps.getPassengerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Passenger not found with id: " + ps.getPassengerId()));

            if (!passenger.getUser().getUserId().equals(user.getUserId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "That passenger does not belong to this user");
            }

            FlightSeat fs = seatMap.get(ps.getFlightSeatId());
            BigDecimal fare = FlightSeatService.fareFor(
                    flight.getBasePrice(), fs.getSeat().getSeatClass());
            total = total.add(fare);

            fs.setSeatStatus(SeatStatus.BOOKED);

            tickets.add(Ticket.builder()
                    .booking(booking)
                    .passenger(passenger)
                    .flightSeat(fs)
                    .fare(fare)
                    .ticketStatus(TicketStatus.CONFIRMED)
                    .build());
        }

        booking.setTotalAmount(total);
        ticketRepository.saveAll(tickets);

        log.info("Created booking {} with {} tickets, total {}",
                booking.getBookingId(), tickets.size(), total);

        return toResponse(booking, tickets);
    }

    /**
     * Cancels a booking and RELEASES the seats.
     *
     * Releasing is the step people forget. Without it the seats stay BOOKED
     * forever and the flight silently sells out.
     */
    @Transactional
    public BookingResponse cancel(Integer bookingId) {

        Booking booking = findOrThrow(bookingId);

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This booking is already cancelled");
        }

        List<Ticket> tickets = ticketRepository.findByBooking_BookingId(bookingId);

        for (Ticket ticket : tickets) {
            ticket.setTicketStatus(TicketStatus.CANCELLED);
            ticket.setCancelledAt(LocalDateTime.now());
            ticket.getFlightSeat().setSeatStatus(SeatStatus.AVAILABLE);  // give the seat back
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        ticketRepository.saveAll(tickets);

        log.info("Cancelled booking {} and released {} seats", bookingId, tickets.size());

        return toResponse(booking, tickets);
    }

    @Transactional(readOnly = true)
    public BookingResponse getById(Integer id) {
        Booking booking = findOrThrow(id);
        return toResponse(booking, ticketRepository.findByBooking_BookingId(id));
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getByUser(Integer userId) {
        return bookingRepository.findByUser_UserIdOrderByBookingDateDesc(userId)
                .stream()
                .map(b -> toResponse(b, ticketRepository.findByBooking_BookingId(b.getBookingId())))
                .toList();
    }

    // ---------- helpers ----------

    private Booking findOrThrow(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Booking not found with id: " + id));
    }

    private BookingResponse toResponse(Booking b, List<Ticket> tickets) {

        List<TicketResponse> ticketResponses = tickets.stream()
                .map(t -> new TicketResponse(
                        t.getTicketId(),
                        t.getPassenger().getPassengerId(),
                        t.getPassenger().getPassengerName(),
                        t.getFlightSeat().getSeat().getSeatNumber(),
                        t.getFlightSeat().getSeat().getSeatClass().name(),
                        t.getFare(),
                        t.getTicketStatus().name(),
                        t.getCancelledAt()))
                .toList();

        Flight f = b.getFlight();

        return new BookingResponse(
                b.getBookingId(),
                b.getUser().getUserId(),
                f.getFlightId(),
                f.getFlightNumber(),
                f.getAirline().getAirlineName(),
                f.getDepartureAirport().getAirportCode(),
                f.getArrivalAirport().getAirportCode(),
                f.getDepartureTime(),
                b.getBookingDate(),
                b.getTotalAmount(),
                b.getBookingStatus().name(),
                ticketResponses);
    }
}
