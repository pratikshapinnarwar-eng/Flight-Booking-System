package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * One passenger, in one seat, on one booking.
 *
 * @OneToOne on flightSeat with unique = true is what makes it IMPOSSIBLE to
 * sell the same seat twice. The database rejects the second insert.
 */
@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Integer ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_seat_id", nullable = false, unique = true)
    private FlightSeat flightSeat;

    @Column(name = "fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    /** CONFIRMED / CANCELLED */
    @Column(name = "ticket_status", nullable = false, length = 20)
    private String ticketStatus;

    /** Null unless the ticket has been cancelled. */
    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
}
