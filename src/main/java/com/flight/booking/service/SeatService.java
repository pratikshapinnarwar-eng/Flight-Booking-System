package com.flight.booking.service;

import com.flight.booking.entity.Aircraft;
import com.flight.booking.entity.Seat;
import com.flight.booking.enums.SeatClass;
import com.flight.booking.enums.SeatType;
import com.flight.booking.model.GenerateSeatsRequest;
import com.flight.booking.model.SeatRequest;
import com.flight.booking.model.SeatResponse;
import com.flight.booking.repository.AircraftRepository;
import com.flight.booking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final AircraftRepository aircraftRepository;

    /** Columns on a standard narrow-body aircraft. */
    private static final char[] COLUMNS = {'A', 'B', 'C', 'D', 'E', 'F'};

    @Transactional
    public SeatResponse create(SeatRequest req) {
        Aircraft aircraft = findAircraft(req.getAircraftId());

        if (seatRepository.existsByAircraft_AircraftIdAndSeatNumber(
                req.getAircraftId(), req.getSeatNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Seat " + req.getSeatNumber() + " already exists on this aircraft");
        }

        Seat seat = Seat.builder()
                .aircraft(aircraft)
                .seatNumber(req.getSeatNumber())
                .seatClass(SeatClass.valueOf(req.getSeatClass()))
                .seatType(SeatType.valueOf(req.getSeatType()))
                .build();

        return toResponse(seatRepository.save(seat));
    }

    /**
     * Builds a whole seat map in one call. 30 rows produces 180 seats
     * (30 x 6 columns) rather than 180 separate POST requests.
     */
    @Transactional
    public List<SeatResponse> generate(GenerateSeatsRequest req) {
        Aircraft aircraft = findAircraft(req.getAircraftId());

        if (seatRepository.countByAircraft_AircraftId(req.getAircraftId()) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This aircraft already has seats. Delete them first to regenerate.");
        }

        int businessRows = req.getBusinessRows() == null ? 0 : req.getBusinessRows();
        if (businessRows > req.getRows()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Business rows cannot exceed total rows");
        }

        List<Seat> seats = new ArrayList<>();

        for (int row = 1; row <= req.getRows(); row++) {
            SeatClass seatClass = row <= businessRows ? SeatClass.BUSINESS : SeatClass.ECONOMY;

            for (char column : COLUMNS) {
                seats.add(Seat.builder()
                        .aircraft(aircraft)
                        .seatNumber(row + String.valueOf(column))
                        .seatClass(seatClass)
                        .seatType(typeFor(column))
                        .build());
            }
        }

        return seatRepository.saveAll(seats).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<SeatResponse> getByAircraft(Integer aircraftId) {
        return seatRepository.findByAircraft_AircraftId(aircraftId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public SeatResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public void delete(Integer id) {
        seatRepository.delete(findOrThrow(id));
    }

    // ---------- helpers ----------

    private SeatType typeFor(char column) {
        return switch (column) {
            case 'A', 'F' -> SeatType.WINDOW;
            case 'C', 'D' -> SeatType.AISLE;
            default       -> SeatType.MIDDLE;
        };
    }

    private Aircraft findAircraft(Integer id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Aircraft not found with id: " + id));
    }

    private Seat findOrThrow(Integer id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Seat not found with id: " + id));
    }

    private SeatResponse toResponse(Seat s) {
        return new SeatResponse(s.getSeatId(), s.getAircraft().getAircraftId(),
                s.getSeatNumber(), s.getSeatClass().name(), s.getSeatType().name());
    }
}
