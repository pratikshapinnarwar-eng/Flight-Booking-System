package com.flight.booking.controller;

import com.flight.booking.model.PaymentRequest;
import com.flight.booking.model.PaymentResponse;
import com.flight.booking.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /** Pay for a booking. Confirms it on success, cancels and frees seats on failure. */
    @PostMapping
    public ResponseEntity<PaymentResponse> pay(@Valid @RequestBody PaymentRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.pay(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getById(id));
    }

    /** All payment attempts for one booking, including failed ones. */
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PaymentResponse>> getByBooking(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(paymentService.getByBooking(bookingId));
    }

    @PatchMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refund(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.refund(id));
    }
}
