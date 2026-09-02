package com.flight.booking.controller;

import com.flight.booking.model.AirlineRequest;
import com.flight.booking.model.AirlineResponse;
import com.flight.booking.service.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST endpoints for airlines.
 *
 * A controller does exactly three things:
 *   1. receive the HTTP request
 *   2. call the service
 *   3. wrap the result with the right status code
 *
 * If you ever find a business rule (an "if") in here, move it to the service.
 */
@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
@Tag(name = "Airlines", description = "Create and manage airlines")
public class AirlineController {

    private final AirlineService airlineService;

    @Operation(summary = "Create a new airline")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Airline created"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "409", description = "Airline code already exists")
    })
    @PostMapping
    public ResponseEntity<AirlineResponse> create(@Valid @RequestBody AirlineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.create(request));
    }

    @Operation(summary = "Get all airlines")
    @GetMapping
    public ResponseEntity<List<AirlineResponse>> getAll() {
        return ResponseEntity.ok(airlineService.getAll());
    }

    @Operation(summary = "Get one airline by its id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Airline found"),
            @ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(airlineService.getById(id));
    }

    @Operation(summary = "Get one airline by its IATA code, for example 6E")
    @GetMapping("/code/{code}")
    public ResponseEntity<AirlineResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(airlineService.getByCode(code));
    }

    @Operation(summary = "Update an existing airline")
    @PutMapping("/{id}")
    public ResponseEntity<AirlineResponse> update(@PathVariable Integer id,
                                                  @Valid @RequestBody AirlineRequest request) {
        return ResponseEntity.ok(airlineService.update(id, request));
    }

    @Operation(summary = "Delete an airline")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Airline deleted"),
            @ApiResponse(responseCode = "404", description = "Airline not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        airlineService.delete(id);
        return ResponseEntity.noContent().build();   // 204, empty body
    }
}
