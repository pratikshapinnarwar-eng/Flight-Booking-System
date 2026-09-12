package com.flight.booking.entity;

import com.flight.booking.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * One passenger, one seat, one booking.
 * The UNIQUE constraint on flight_seat_id makes selling a seat twice impossible.
 */
@Entity
@Table(name = "ticket",
       uniqueConstraints = @UniqueConstraint(columnNames = "flight_seat_id"))
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false, length = 20)
    private TicketStatus ticketStatus;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
}
