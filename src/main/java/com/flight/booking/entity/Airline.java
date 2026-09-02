package com.flight.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Maps to the "airline" table in MySQL.
 *
 * Use @Getter/@Setter, NOT Lombok @Data. @Data also generates equals(),
 * hashCode() and toString(), which on a JPA entity walk into related
 * entities and can cause infinite loops and surprise database queries.
 */
@Entity
@Table(name = "airline")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // MySQL AUTO_INCREMENT
    @Column(name = "airline_id")
    private Integer airlineId;

    @Column(name = "airline_name", nullable = false, length = 100)
    private String airlineName;

    @Column(name = "airline_code", nullable = false, unique = true, length = 10)
    private String airlineCode;
}
