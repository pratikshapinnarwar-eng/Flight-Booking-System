package com.flight.booking.entity;

import com.flight.booking.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.*;

/**
 * A physical Seat, on ONE specific Flight, with its availability.
 *
 * This is the table that makes the whole system work. Seat 12A can be BOOKED
 * on Monday's flight and AVAILABLE on Tuesday's, even though it is the same
 * physical seat on the same aircraft.
 */
@Entity
@Table(name = "flight_seat",
       uniqueConstraints = @UniqueConstraint(columnNames = {"flight_id", "seat_id"}))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSeat extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status", nullable = false, length = 20)
    private SeatStatus seatStatus;
}
