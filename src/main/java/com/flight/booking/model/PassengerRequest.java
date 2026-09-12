package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerRequest {

    @JsonProperty("user_id")
    @NotNull(message = "user_id is required")
    private Integer userId;

    @JsonProperty("passenger_name")
    @NotBlank(message = "passenger_name is required")
    @Size(max = 100)
    private String passengerName;

    @JsonProperty("date_of_birth")
    @Past(message = "date_of_birth must be in the past")
    private LocalDate dateOfBirth;

    @JsonProperty("gender")
    @Pattern(regexp = "MALE|FEMALE|OTHER", message = "gender must be MALE, FEMALE or OTHER")
    private String gender;

    @JsonProperty("nationality")
    @Size(max = 60)
    private String nationality;

    @JsonProperty("passport_no")
    @Size(max = 30)
    private String passportNo;

    @JsonProperty("passport_expiry")
    private LocalDate passportExpiry;
}
