package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * A person who will actually travel. Distinct from User: one user account can
 * save several passengers (family members, colleagues) and book for any of them.
 */
@Entity
@Table(name = "passenger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer passengerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "passenger_name", nullable = false, length = 100)
    private String passengerName;

    /** LocalDate, not LocalDateTime - a birth date has no time component. */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 15)
    private String gender;

    @Column(name = "nationality", length = 60)
    private String nationality;

    @Column(name = "passport_no", unique = true, length = 30)
    private String passportNo;

    @Column(name = "passport_expiry")
    private LocalDate passportExpiry;
}
