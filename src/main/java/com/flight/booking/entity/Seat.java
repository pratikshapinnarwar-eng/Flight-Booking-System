package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A PHYSICAL seat on an aircraft. Seat 12A exists on aircraft #5 permanently.
 * Whether it is free on a given flight is tracked by FlightSeat, not here.
 */
@Entity
@Table(name = "seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    /** ECONOMY / BUSINESS / FIRST */
    @Column(name = "seat_class", nullable = false, length = 20)
    private String seatClass;

    /** WINDOW / MIDDLE / AISLE */
    @Column(name = "seat_type", nullable = false, length = 20)
    private String seatType;
}
