package com.flight.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PassengerResponse {

    @JsonProperty("passenger_id")    private Integer passengerId;
    @JsonProperty("user_id")         private Integer userId;
    @JsonProperty("passenger_name")  private String passengerName;
    @JsonProperty("date_of_birth")   private LocalDate dateOfBirth;
    @JsonProperty("gender")          private String gender;
    @JsonProperty("nationality")     private String nationality;
    @JsonProperty("passport_no")     private String passportNo;
    @JsonProperty("passport_expiry") private LocalDate passportExpiry;
}
