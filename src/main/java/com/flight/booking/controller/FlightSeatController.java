package com.flight.booking.controller;

import com.flight.booking.model.FlightSeatResponse;
import com.flight.booking.service.FlightSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/flight-seats")
@RequiredArgsConstructor
public class FlightSeatController {

    private final FlightSeatService flightSeatService;

    /**
     * Run this ONCE after creating a flight. Copies the aircraft's seat map
     * onto the flight, all marked AVAILABLE.
     *
     * POST /api/flight-seats/generate/3
     */
    @PostMapping("/generate/{flightId}")
    public ResponseEntity<List<FlightSeatResponse>> generate(@PathVariable Integer flightId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightSeatService.generate(flightId));
    }

    /** The full seat map for a flight - what the seat picker renders. */
    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightSeatResponse>> getByFlight(@PathVariable Integer flightId) {
        return ResponseEntity.ok(flightSeatService.getByFlight(flightId));
    }

    /** Only the seats still free. */
    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<FlightSeatResponse>> getAvailable(@PathVariable Integer flightId) {
        return ResponseEntity.ok(flightSeatService.getAvailable(flightId));
    }

    /** Just the number, for the search results list. */
    @GetMapping("/flight/{flightId}/available/count")
    public ResponseEntity<Map<String, Long>> countAvailable(@PathVariable Integer flightId) {
        return ResponseEntity.ok(Map.of("available_seats",
                flightSeatService.countAvailable(flightId)));
    }
}
