package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A physical Seat, on ONE specific Flight, with its availability.
 *
 * This is the table that makes the whole system work: seat 12A can be BOOKED
 * on Monday's flight and AVAILABLE on Tuesday's, even though it is the same
 * physical seat on the same aircraft.
 */
@Entity
@Table(name = "flight_seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_seat_id")
    private Integer flightSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    /** AVAILABLE / BOOKED / BLOCKED */
    @Column(name = "seat_status", nullable = false, length = 20)
    private String seatStatus;
}
