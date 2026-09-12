package com.flight.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Audit fields shared by every entity.
 *
 * @MappedSuperclass means this is NOT a table. Its columns are copied into
 * whichever entity extends it, so these two fields are written once instead
 * of being repeated in all twelve entities.
 *
 * @PrePersist runs before the first INSERT, @PreUpdate before every UPDATE,
 * so the timestamps maintain themselves.
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
