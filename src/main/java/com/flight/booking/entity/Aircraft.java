package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Maps to the "aircraft" table.
 *
 * fetch = LAZY means the Airline is only loaded from the database when you
 * actually call getAirline(). The default is EAGER, which quietly fires an
 * extra query every time an Aircraft is loaded.
 */
@Entity
@Table(name = "aircraft")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aircraft_id")
    private Integer aircraftId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @Column(name = "model", nullable = false, length = 80)
    private String model;
}
