package com.flight.booking.controller;

import com.flight.booking.model.AirlineRequest;
import com.flight.booking.model.AirlineResponse;
import com.flight.booking.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> create(@Valid @RequestBody AirlineRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.create(req));
    }

    @GetMapping
    public ResponseEntity<List<AirlineResponse>> getAll() {
        return ResponseEntity.ok(airlineService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(airlineService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody AirlineRequest req) {
        return ResponseEntity.ok(airlineService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        airlineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
