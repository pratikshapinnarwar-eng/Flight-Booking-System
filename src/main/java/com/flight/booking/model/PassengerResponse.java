package com.flight.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PassengerResponse {
    private Integer passengerId;
    private Integer userId;
    private String passengerName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String passportNo;
    private LocalDate passportExpiry;
}
