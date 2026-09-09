package com.flight.booking.service;

import com.flight.booking.entity.Airport;
import com.flight.booking.model.AirportRequest;
import com.flight.booking.model.AirportResponse;
import com.flight.booking.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportResponse create(AirportRequest req) {
        if (airportRepository.existsByAirportCode(req.getAirportCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Airport code already exists: " + req.getAirportCode());
        }
        Airport airport = Airport.builder()
                .airportName(req.getAirportName())
                .city(req.getCity())
                .country(req.getCountry())
                .airportCode(req.getAirportCode())
                .build();
        return toResponse(airportRepository.save(airport));
    }

    public List<AirportResponse> getAll() {
        return airportRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AirportResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    public List<AirportResponse> searchByCity(String city) {
        return airportRepository.findByCityContainingIgnoreCase(city)
                .stream().map(this::toResponse).toList();
    }

    public AirportResponse update(Integer id, AirportRequest req) {
        Airport airport = findOrThrow(id);
        airport.setAirportName(req.getAirportName());
        airport.setCity(req.getCity());
        airport.setCountry(req.getCountry());
        airport.setAirportCode(req.getAirportCode());
        return toResponse(airportRepository.save(airport));
    }

    public void delete(Integer id) {
        airportRepository.delete(findOrThrow(id));
    }

    private Airport findOrThrow(Integer id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Airport not found with id: " + id));
    }

    private AirportResponse toResponse(Airport a) {
        return new AirportResponse(a.getAirportId(), a.getAirportName(),
                a.getCity(), a.getCountry(), a.getAirportCode());
    }
}
