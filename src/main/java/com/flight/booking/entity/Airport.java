package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Maps to the "airport" table. */
@Entity
@Table(name = "airport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Integer airportId;

    @Column(name = "airport_name", nullable = false, length = 120)
    private String airportName;

    @Column(name = "city", nullable = false, length = 80)
    private String city;

    @Column(name = "country", nullable = false, length = 80)
    private String country;

    @Column(name = "airport_code", nullable = false, unique = true, length = 10)
    private String airportCode;
}
