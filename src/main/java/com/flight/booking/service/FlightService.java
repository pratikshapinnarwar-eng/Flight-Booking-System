package com.flight.booking.service;

import com.flight.booking.entity.Aircraft;
import com.flight.booking.entity.Airline;
import com.flight.booking.entity.Airport;
import com.flight.booking.entity.Flight;
import com.flight.booking.enums.FlightStatus;
import com.flight.booking.model.FlightRequest;
import com.flight.booking.model.FlightResponse;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.repository.AirlineRepository;
import com.flight.booking.repository.AirportRepository;
import com.flight.booking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;

    @Transactional
    public FlightResponse create(FlightRequest req) {

        if (req.getDepartureAirportId().equals(req.getArrivalAirportId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Departure and arrival airports cannot be the same"
            );
        }

        if (!req.getArrivalTime().isAfter(req.getDepartureTime())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Arrival time must be after departure time"
            );
        }

        Airline airline = findAirline(req.getAirlineId());
        Aircraft aircraft = findAircraft(req.getAircraftId());
        Airport departure = findAirport(req.getDepartureAirportId());
        Airport arrival = findAirport(req.getArrivalAirportId());

        // The aircraft must belong to the airline operating the flight
        if (!aircraft.getAirline().getAirlineId()
                .equals(airline.getAirlineId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "That aircraft does not belong to the selected airline"
            );
        }

        Flight flight = Flight.builder()
                .flightNumber(req.getFlightNumber())
                .airline(airline)
                .aircraft(aircraft)
                .departureAirport(departure)
                .arrivalAirport(arrival)
                .departureTime(req.getDepartureTime())
                .arrivalTime(req.getArrivalTime())
                .basePrice(req.getBasePrice())
                .status(
                        req.getStatus() == null
                                ? FlightStatus.SCHEDULED
                                : FlightStatus.valueOf(req.getStatus())
                )
                .build();

        return toResponse(flightRepository.save(flight));
    }

    @Transactional(readOnly = true)
    public List<FlightResponse> search(
            String from,
            String to,
            LocalDate date) {

        if (from.equalsIgnoreCase(to)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Departure and arrival airports cannot be the same"
            );
        }

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return flightRepository
                .search(
                        from,
                        to,
                        start,
                        end,
                        FlightStatus.SCHEDULED
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FlightResponse> getAll() {

        return flightRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FlightResponse getById(Integer id) {

        return toResponse(findOrThrow(id));
    }

    @Transactional
    public FlightResponse update(
            Integer id,
            FlightRequest req) {

        Flight flight = findOrThrow(id);

        flight.setFlightNumber(req.getFlightNumber());
        flight.setAirline(findAirline(req.getAirlineId()));
        flight.setAircraft(findAircraft(req.getAircraftId()));
        flight.setDepartureAirport(
                findAirport(req.getDepartureAirportId())
        );
        flight.setArrivalAirport(
                findAirport(req.getArrivalAirportId())
        );
        flight.setDepartureTime(req.getDepartureTime());
        flight.setArrivalTime(req.getArrivalTime());
        flight.setBasePrice(req.getBasePrice());

        if (req.getStatus() != null) {
            flight.setStatus(
                    FlightStatus.valueOf(req.getStatus())
            );
        }

        return toResponse(flightRepository.save(flight));
    }

    @Transactional
    public FlightResponse updateStatus(
            Integer id,
            String status) {

        Flight flight = findOrThrow(id);

        try {
            flight.setStatus(
                    FlightStatus.valueOf(status.toUpperCase())
            );

        } catch (IllegalArgumentException e) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Use SCHEDULED, DELAYED, CANCELLED, DEPARTED or ARRIVED"
            );
        }

        return toResponse(flightRepository.save(flight));
    }

    @Transactional
    public void delete(Integer id) {

        flightRepository.delete(findOrThrow(id));
    }

    // ---------- Helpers ----------

    private Airline findAirline(Integer id) {

        return airlineRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Airline not found with id: " + id
                        )
                );
    }

    private Aircraft findAircraft(Integer id) {

        return aircraftRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Aircraft not found with id: " + id
                        )
                );
    }

    private Airport findAirport(Integer id) {

        return airportRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Airport not found with id: " + id
                        )
                );
    }

    private Flight findOrThrow(Integer id) {

        return flightRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Flight not found with id: " + id
                        )
                );
    }

    // ---------- Response Mapping ----------

    private FlightResponse toResponse(Flight f) {

        Duration d = Duration.between(
                f.getDepartureTime(),
                f.getArrivalTime()
        );

        String duration =
                d.toHours() + "h " +
                        d.toMinutesPart() + "m";

        return new FlightResponse(

                // Flight
                f.getFlightId(),
                f.getFlightNumber(),

                // Airline
                f.getAirline().getAirlineId(),
                f.getAirline().getAirlineName(),
                f.getAirline().getAirlineCode(),

                // Aircraft
                f.getAircraft().getAircraftId(),
                f.getAircraft().getModel(),

                // Departure Airport
                f.getDepartureAirport().getAirportId(),
                f.getDepartureAirport().getAirportCode(),
                f.getDepartureAirport().getCity(),

                // Arrival Airport
                f.getArrivalAirport().getAirportId(),
                f.getArrivalAirport().getAirportCode(),
                f.getArrivalAirport().getCity(),

                // Time
                f.getDepartureTime(),
                f.getArrivalTime(),
                duration,

                // Price and Status
                f.getBasePrice(),
                f.getStatus().name()
        );
    }
}
