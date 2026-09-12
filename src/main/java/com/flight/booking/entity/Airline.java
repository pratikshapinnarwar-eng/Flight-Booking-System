package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airline")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private Integer airlineId;

    @Column(name = "airline_name", nullable = false)
    private String airlineName;

    @Column(name = "airline_code", nullable = false, unique = true, length = 10)
    private String airlineCode;
}
