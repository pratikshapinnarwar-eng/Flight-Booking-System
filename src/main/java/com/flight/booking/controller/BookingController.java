package com.flight.booking.controller;

import com.flight.booking.model.BookingRequest;
import com.flight.booking.model.BookingResponse;
import com.flight.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /** Creates the booking and one ticket per passenger. */
    @PostMapping
    public ResponseEntity<BookingResponse> create(@Valid @RequestBody BookingRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    /** Booking history for one user. */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getByUser(userId));
    }

    /** Cancels the booking and releases the seats back to AVAILABLE. */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancel(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.cancel(id));
    }
}
