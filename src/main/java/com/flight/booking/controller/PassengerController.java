package com.flight.booking.controller;

import com.flight.booking.model.PassengerRequest;
import com.flight.booking.model.PassengerResponse;
import com.flight.booking.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> create(@Valid @RequestBody PassengerRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerService.create(req));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PassengerResponse>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(passengerService.getByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(passengerService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        passengerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
