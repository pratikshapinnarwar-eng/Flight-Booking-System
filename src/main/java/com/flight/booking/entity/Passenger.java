package com.flight.booking.entity;

import com.flight.booking.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/** A person who travels. One user account can save several passengers. */
@Entity
@Table(name = "passenger")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Integer passengerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "passenger_name", nullable = false, length = 100)
    private String passengerName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 15)
    private Gender gender;

    @Column(name = "nationality", length = 60)
    private String nationality;

    @Column(name = "passport_no", unique = true, length = 30)
    private String passportNo;

    @Column(name = "passport_expiry")
    private LocalDate passportExpiry;
}
