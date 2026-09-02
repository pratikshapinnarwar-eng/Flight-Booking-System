package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/** Maps to the "admin" table. */
@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "admin_name", nullable = false, length = 100)
    private String adminName;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /** SUPER_ADMIN / AIRLINE_ADMIN */
    @Column(name = "role", nullable = false, length = 30)
    private String role;

    /** Nullable: a SUPER_ADMIN does not belong to any one airline. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
