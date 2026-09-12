package com.flight.booking.controller;

import com.flight.booking.model.AirportRequest;
import com.flight.booking.model.AirportResponse;
import com.flight.booking.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<AirportResponse> create(@Valid @RequestBody AirportRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airportService.create(req));
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAll() {
        return ResponseEntity.ok(airportService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(airportService.getById(id));
    }

    /** GET /api/airports/search?city=Mumbai */
    @GetMapping("/search")
    public ResponseEntity<List<AirportResponse>> searchByCity(@RequestParam String city) {
        return ResponseEntity.ok(airportService.searchByCity(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody AirportRequest req) {
        return ResponseEntity.ok(airportService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        airportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
