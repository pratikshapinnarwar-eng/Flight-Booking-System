package com.flight.booking.service;

import com.flight.booking.entity.Flight;
import com.flight.booking.entity.FlightSeat;
import com.flight.booking.entity.Seat;
import com.flight.booking.enums.SeatClass;
import com.flight.booking.enums.SeatStatus;
import com.flight.booking.model.FlightSeatResponse;
import com.flight.booking.repository.FlightRepository;
import com.flight.booking.repository.FlightSeatRepository;
import com.flight.booking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSeatService {

    private final FlightSeatRepository flightSeatRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    /**
     * Copies the aircraft's seat map onto one flight, all AVAILABLE.
     * Run once per flight, after the aircraft has seats.
     */
    @Transactional
    public List<FlightSeatResponse> generate(Integer flightId) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Flight not found with id: " + flightId));

        if (flightSeatRepository.countByFlight_FlightId(flightId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Seats have already been generated for flight " + flightId);
        }

        List<Seat> seats = seatRepository.findByAircraft_AircraftId(
                flight.getAircraft().getAircraftId());

        if (seats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Aircraft " + flight.getAircraft().getAircraftId()
                    + " has no seats. Create them first with POST /api/seats/generate");
        }

        List<FlightSeat> flightSeats = seats.stream()
                .map(seat -> FlightSeat.builder()
                        .flight(flight)
                        .seat(seat)
                        .seatStatus(SeatStatus.AVAILABLE)
                        .build())
                .toList();

        return flightSeatRepository.saveAll(flightSeats)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<FlightSeatResponse> getByFlight(Integer flightId) {
        return flightSeatRepository.findByFlight_FlightId(flightId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<FlightSeatResponse> getAvailable(Integer flightId) {
        return flightSeatRepository
                .findByFlight_FlightIdAndSeatStatus(flightId, SeatStatus.AVAILABLE)
                .stream().map(this::toResponse).toList();
    }

    /** Business costs 2.5x economy, first class 4x. */
    public static BigDecimal fareFor(BigDecimal basePrice, SeatClass seatClass) {
        BigDecimal multiplier = switch (seatClass) {
            case ECONOMY  -> new BigDecimal("1.0");
            case BUSINESS -> new BigDecimal("2.5");
            case FIRST    -> new BigDecimal("4.0");
        };
        return basePrice.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }

    private FlightSeatResponse toResponse(FlightSeat fs) {
        return new FlightSeatResponse(
                fs.getFlightSeatId(),
                fs.getFlight().getFlightId(),
                fs.getSeat().getSeatNumber(),
                fs.getSeat().getSeatClass().name(),
                fs.getSeat().getSeatType().name(),
                fs.getSeatStatus().name(),
                fareFor(fs.getFlight().getBasePrice(), fs.getSeat().getSeatClass()));
    }
}
