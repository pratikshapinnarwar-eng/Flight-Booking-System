package com.flight.booking.controller;

import com.flight.booking.model.GenerateSeatsRequest;
import com.flight.booking.model.SeatRequest;
import com.flight.booking.model.SeatResponse;
import com.flight.booking.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<SeatResponse> create(@Valid @RequestBody SeatRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.create(req));
    }

    /**
     * Creates a whole seat map at once.
     * POST /api/seats/generate  {"aircraftId":1,"rows":30,"businessRows":3}
     */
    @PostMapping("/generate")
    public ResponseEntity<List<SeatResponse>> generate(@Valid @RequestBody GenerateSeatsRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seatService.generate(req));
    }

    @GetMapping("/aircraft/{aircraftId}")
    public ResponseEntity<List<SeatResponse>> getByAircraft(@PathVariable Integer aircraftId) {
        return ResponseEntity.ok(seatService.getByAircraft(aircraftId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(seatService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
