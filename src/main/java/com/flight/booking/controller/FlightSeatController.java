package com.flight.booking.controller;

import com.flight.booking.model.FlightSeatResponse;
import com.flight.booking.service.FlightSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight-seats")
@RequiredArgsConstructor
public class FlightSeatController {

    private final FlightSeatService flightSeatService;

    /** Run ONCE per flight. Copies the aircraft seat map onto the flight. */
    @PostMapping("/generate/{flightId}")
    public ResponseEntity<List<FlightSeatResponse>> generate(@PathVariable Integer flightId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightSeatService.generate(flightId));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightSeatResponse>> getByFlight(@PathVariable Integer flightId) {
        return ResponseEntity.ok(flightSeatService.getByFlight(flightId));
    }

    @GetMapping("/flight/{flightId}/available")
    public ResponseEntity<List<FlightSeatResponse>> getAvailable(@PathVariable Integer flightId) {
        return ResponseEntity.ok(flightSeatService.getAvailable(flightId));
    }
}
