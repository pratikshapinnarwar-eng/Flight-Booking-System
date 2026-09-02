package com.flight.booking.service.impl;

import com.flight.booking.entity.Airline;
import com.flight.booking.exception.DuplicateResourceException;
import com.flight.booking.exception.ResourceNotFoundException;
import com.flight.booking.model.AirlineRequest;
import com.flight.booking.model.AirlineResponse;
import com.flight.booking.repository.AirlineRepository;
import com.flight.booking.service.AirlineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ALL business rules for airlines live here. The controller has none.
 *
 * @RequiredArgsConstructor (Lombok) generates a constructor taking every final
 * field, and Spring injects the beans through it. Constructor injection is
 * preferred over @Autowired on fields: dependencies are visible, the class is
 * testable without Spring, and the fields can be final.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    @Transactional
    public AirlineResponse create(AirlineRequest request) {

        // Business rule: airline_code must be unique.
        // The database enforces it too, but checking here lets us return a
        // friendly 409 instead of an ugly constraint-violation 500.
        if (airlineRepository.existsByAirlineCode(request.getAirlineCode())) {
            throw new DuplicateResourceException("Airline", "airlineCode", request.getAirlineCode());
        }

        Airline airline = Airline.builder()
                .airlineName(request.getAirlineName())
                .airlineCode(request.getAirlineCode())
                .build();

        Airline saved = airlineRepository.save(airline);
        log.info("Created airline id={} code={}", saved.getAirlineId(), saved.getAirlineCode());

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)   // readOnly lets the database skip write locks
    public List<AirlineResponse> getAll() {
        return airlineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AirlineResponse getByCode(String code) {
        Airline airline = airlineRepository.findByAirlineCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "airlineCode", code));
        return toResponse(airline);
    }

    @Override
    @Transactional
    public AirlineResponse update(Integer id, AirlineRequest request) {
        Airline airline = findOrThrow(id);

        if (airlineRepository.existsByAirlineCodeAndAirlineIdNot(request.getAirlineCode(), id)) {
            throw new DuplicateResourceException("Airline", "airlineCode", request.getAirlineCode());
        }

        airline.setAirlineName(request.getAirlineName());
        airline.setAirlineCode(request.getAirlineCode());
        // No explicit save() needed: inside a transaction JPA flushes changes to
        // a managed entity automatically. Calling save() would also be correct.

        log.info("Updated airline id={}", id);
        return toResponse(airline);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        airlineRepository.delete(findOrThrow(id));
        log.info("Deleted airline id={}", id);
    }

    // ---------------- private helpers ----------------

    private Airline findOrThrow(Integer id) {
        return airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", id));
    }

    /** Entity -> DTO. Written once, used everywhere in this class. */
    private AirlineResponse toResponse(Airline airline) {
        return AirlineResponse.builder()
                .airlineId(airline.getAirlineId())
                .airlineName(airline.getAirlineName())
                .airlineCode(airline.getAirlineCode())
                .build();
    }
}
