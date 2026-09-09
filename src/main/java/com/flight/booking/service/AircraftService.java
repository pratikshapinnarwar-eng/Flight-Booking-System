package com.flight.booking.service;

import com.flight.booking.entity.Aircraft;
import com.flight.booking.entity.Airline;
import com.flight.booking.model.AircraftRequest;
import com.flight.booking.model.AircraftResponse;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Transactional
    public AircraftResponse create(AircraftRequest req) {
        // Never trust an id from the client - load the real airline and 404 if absent
        Airline airline = airlineRepository.findById(req.getAirlineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Airline not found with id: " + req.getAirlineId()));

        Aircraft aircraft = Aircraft.builder()
                .airline(airline)
                .model(req.getModel())
                .build();

        return toResponse(aircraftRepository.save(aircraft));
    }

    /** readOnly keeps the session open so the LAZY airline can be read. */
    @Transactional(readOnly = true)
    public List<AircraftResponse> getAll() {
        return aircraftRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AircraftResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<AircraftResponse> getByAirline(Integer airlineId) {
        return aircraftRepository.findByAirline_AirlineId(airlineId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public AircraftResponse update(Integer id, AircraftRequest req) {
        Aircraft aircraft = findOrThrow(id);
        Airline airline = airlineRepository.findById(req.getAirlineId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Airline not found with id: " + req.getAirlineId()));
        aircraft.setAirline(airline);
        aircraft.setModel(req.getModel());
        return toResponse(aircraftRepository.save(aircraft));
    }

    @Transactional
    public void delete(Integer id) {
        aircraftRepository.delete(findOrThrow(id));
    }

    private Aircraft findOrThrow(Integer id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aircraft not found with id: " + id));
    }

    private AircraftResponse toResponse(Aircraft a) {
        return new AircraftResponse(a.getAircraftId(), a.getModel(),
                a.getAirline().getAirlineId(), a.getAirline().getAirlineName());
    }
}
