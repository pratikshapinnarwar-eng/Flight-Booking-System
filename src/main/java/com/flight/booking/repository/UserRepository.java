package com.flight.booking.repository;

import com.flight.booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data writes the implementation for us. The SQL is derived from the
 * method NAME: findByEmail becomes SELECT * FROM users WHERE email = ?
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
