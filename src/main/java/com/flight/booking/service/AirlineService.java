package com.flight.booking.service;

import com.flight.booking.entity.Airline;
import com.flight.booking.model.AirlineRequest;
import com.flight.booking.model.AirlineResponse;
import com.flight.booking.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineResponse create(AirlineRequest req) {
        if (airlineRepository.existsByAirlineCode(req.getAirlineCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Airline code already exists: " + req.getAirlineCode());
        }
        Airline airline = Airline.builder()
                .airlineName(req.getAirlineName())
                .airlineCode(req.getAirlineCode())
                .build();
        return toResponse(airlineRepository.save(airline));
    }

    public List<AirlineResponse> getAll() {
        return airlineRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AirlineResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    public AirlineResponse update(Integer id, AirlineRequest req) {
        Airline airline = findOrThrow(id);
        airline.setAirlineName(req.getAirlineName());
        airline.setAirlineCode(req.getAirlineCode());
        return toResponse(airlineRepository.save(airline));
    }

    public void delete(Integer id) {
        airlineRepository.delete(findOrThrow(id));
    }

    private Airline findOrThrow(Integer id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Airline not found with id: " + id));
    }

    private AirlineResponse toResponse(Airline a) {
        return new AirlineResponse(a.getAirlineId(), a.getAirlineName(), a.getAirlineCode());
    }
}
