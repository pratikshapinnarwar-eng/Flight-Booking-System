package com.flight.booking.service;

import com.flight.booking.dto.AuthResponse;
import com.flight.booking.dto.LoginRequest;
import com.flight.booking.dto.RegisterRequest;
import com.flight.booking.model.User;
import com.flight.booking.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

/**
 * All the business rules for registration and login live here.
 * The controller has none.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    // BCrypt turns a password into a one-way hash. You can never turn the
    // hash back into the password - that is exactly the point.
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "An account with this email already exists");
        }

        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))  // never store plain text
                .phone(request.getPhone())
                .createdAt(LocalDateTime.now())
                .build();

        User saved = userRepository.save(user);

        return new AuthResponse(saved.getUserId(), saved.getUserName(),
                saved.getEmail(), saved.getPhone(), "Registration successful");
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"));

        // matches() hashes what was typed and compares the two hashes.
        // Nothing is ever decrypted.
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid email or password");
        }

        return new AuthResponse(user.getUserId(), user.getUserName(),
                user.getEmail(), user.getPhone(), "Login successful");
    }
}
