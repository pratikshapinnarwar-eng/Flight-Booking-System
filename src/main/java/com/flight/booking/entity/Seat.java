package com.flight.booking.entity;

import com.flight.booking.enums.SeatClass;
import com.flight.booking.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

/**
 * A PHYSICAL seat on an aircraft. Seat 12A exists on aircraft 5 permanently.
 *
 * Whether it is free on a given flight is NOT stored here - that belongs to
 * flight_seat, which you will add next. Keeping them separate is what lets the
 * same seat be booked on Monday and available on Tuesday.
 */
@Entity
@Table(name = "seat",
       uniqueConstraints = @UniqueConstraint(columnNames = {"aircraft_id", "seat_number"}))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    /** e.g. "12A" */
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_class", nullable = false, length = 20)
    private SeatClass seatClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type", nullable = false, length = 20)
    private SeatType seatType;
}
