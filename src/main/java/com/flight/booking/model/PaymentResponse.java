package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponse {

    @JsonProperty("payment_id")     private Integer paymentId;
    @JsonProperty("booking_id")     private Integer bookingId;
    @JsonProperty("amount")         private BigDecimal amount;
    @JsonProperty("payment_method") private String paymentMethod;
    @JsonProperty("payment_status") private String paymentStatus;
    @JsonProperty("payment_date")   private LocalDateTime paymentDate;
    @JsonProperty("booking_status") private String bookingStatus;
    @JsonProperty("message")        private String message;
}
