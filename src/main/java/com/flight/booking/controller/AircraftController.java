package com.flight.booking.controller;

import com.flight.booking.model.AircraftRequest;
import com.flight.booking.model.AircraftResponse;
import com.flight.booking.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RequiredArgsConstructor
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> create(@Valid @RequestBody AircraftRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.create(req));
    }

    @GetMapping
    public ResponseEntity<List<AircraftResponse>> getAll() {
        return ResponseEntity.ok(aircraftService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(aircraftService.getById(id));
    }

    /** GET /api/aircrafts/airline/1 */
    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<AircraftResponse>> getByAirline(@PathVariable Integer airlineId) {
        return ResponseEntity.ok(aircraftService.getByAirline(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> update(@PathVariable Integer id,
                                                   @Valid @RequestBody AircraftRequest req) {
        return ResponseEntity.ok(aircraftService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        aircraftService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
