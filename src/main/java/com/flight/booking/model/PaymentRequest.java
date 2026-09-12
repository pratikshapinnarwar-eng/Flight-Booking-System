package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

/**
 * {
 *   "booking_id": 1,
 *   "amount": 5200.00,
 *   "payment_method": "UPI",
 *   "simulate_failure": false
 * }
 *
 * simulate_failure lets the frontend test the failure path without a real
 * payment gateway. Remove it once a gateway is integrated.
 */
@Data
public class PaymentRequest {

    @JsonProperty("booking_id")
    @NotNull(message = "booking_id is required")
    private Integer bookingId;

    @JsonProperty("amount")
    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be greater than zero")
    private BigDecimal amount;

    @JsonProperty("payment_method")
    @NotBlank(message = "payment_method is required")
    @Pattern(regexp = "CARD|UPI|NETBANKING|WALLET",
             message = "payment_method must be CARD, UPI, NETBANKING or WALLET")
    private String paymentMethod;

    @JsonProperty("simulate_failure")
    private Boolean simulateFailure = false;
}
