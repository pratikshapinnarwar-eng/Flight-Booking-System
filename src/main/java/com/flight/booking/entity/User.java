package com.flight.booking.entity;

import com.flight.booking.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Maps to the "users" table. Named "users" not "user" because USER is a
 * reserved keyword in PostgreSQL.
 *
 * createdAt and updatedAt are inherited from BaseEntity.
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)   // required when @Data extends a class
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    /**
     * EnumType.STRING stores "USER" in the database. Never use the default
     * ORDINAL - it stores 0 and 1, so reordering the enum later would
     * silently change everyone's role.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
