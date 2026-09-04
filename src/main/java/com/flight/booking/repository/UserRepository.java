package com.flight.booking.repository;

import com.flight.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Spring Data writes the SQL from the method name
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
