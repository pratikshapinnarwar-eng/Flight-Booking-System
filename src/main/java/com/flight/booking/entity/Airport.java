package com.flight.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "airport")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Integer airportId;

    @Column(name = "airport_name", nullable = false)
    private String airportName;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "airport_code", nullable = false, unique = true, length = 10)
    private String airportCode;
}
