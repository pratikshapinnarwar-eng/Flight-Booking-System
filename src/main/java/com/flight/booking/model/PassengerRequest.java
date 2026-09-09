package com.flight.booking.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerRequest {

    @NotNull(message = "User id is required")
    private Integer userId;

    @NotBlank(message = "Passenger name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String passengerName;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "Gender must be MALE, FEMALE or OTHER")
    private String gender;

    @Size(max = 60, message = "Nationality must be at most 60 characters")
    private String nationality;

    @Size(max = 30, message = "Passport number must be at most 30 characters")
    private String passportNo;

    @Future(message = "Passport expiry must be in the future")
    private LocalDate passportExpiry;
}
