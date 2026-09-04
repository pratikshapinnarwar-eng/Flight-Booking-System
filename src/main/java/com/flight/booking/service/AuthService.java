package com.flight.booking.service;

import com.flight.booking.dto.AuthResponse;
import com.flight.booking.dto.LoginRequest;
import com.flight.booking.dto.RegisterRequest;
import com.flight.booking.entity.User;
import com.flight.booking.enums.Role;
import com.flight.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * All the business rules for registration and login. The controller has none.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    // BCrypt is a ONE-WAY hash. You can turn a password into a hash, but you
    // can never turn the hash back into the password. Login works by hashing
    // what was typed and comparing hashes - nothing is ever decrypted.
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthResponse register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = User.builder()
                .userName(req.getUserName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))   // never store plain text
                .phone(req.getPhone())
                .role(Role.USER)                               // server decides, not the client
                .build();

        User saved = userRepository.save(user);

        return new AuthResponse(saved.getUserId(), saved.getUserName(),
                saved.getEmail(), saved.getRole().name(), "Registration successful");
    }

    public AuthResponse login(LoginRequest req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // Note both failures give the SAME message. Saying "no account with
        // that email" would let someone discover which emails are registered.
        return new AuthResponse(user.getUserId(), user.getUserName(),
                user.getEmail(), user.getRole().name(), "Login successful");
    }
}
