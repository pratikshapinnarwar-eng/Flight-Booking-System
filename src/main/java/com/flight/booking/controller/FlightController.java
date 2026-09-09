package com.flight.booking.controller;

import com.flight.booking.model.FlightRequest;
import com.flight.booking.model.FlightResponse;
import com.flight.booking.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    /**
     * The main endpoint the frontend will use.
     * GET /api/flights/search?from=DEL&to=BOM&date=2026-09-15
     *
     * @DateTimeFormat tells Spring how to parse the date string.
     */
    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> search(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(flightService.search(from, to, date));
    }

    @PostMapping
    public ResponseEntity<FlightResponse> create(@Valid @RequestBody FlightRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.create(req));
    }

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAll() {
        return ResponseEntity.ok(flightService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(flightService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> update(@PathVariable Integer id,
                                                 @Valid @RequestBody FlightRequest req) {
        return ResponseEntity.ok(flightService.update(id, req));
    }

    /** PATCH /api/flights/1/status?status=DELAYED */
    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> updateStatus(@PathVariable Integer id,
                                                       @RequestParam String status) {
        return ResponseEntity.ok(flightService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        flightService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
