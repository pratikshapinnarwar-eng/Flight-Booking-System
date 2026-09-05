package com.flight.booking.controllercom.flight.booking.model.AuthResponse;
import com.flight.booking.model.AuthResponse;
import com.flight.booking.model.LoginRequest;
import com.flight.booking.model.RegisterRequest;
import com.flight.booking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Receive the request, call the service, return the right status code.
 *
 * Notice this class only imports from "model", never from "entity". The
 * entity never reaches the controller layer, so the password hash can never
 * be returned by accident.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @CrossOrigin(origins = {\"http://localhost:4200\", \"http://localhost:3000\"}))

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController

