package com.flight.booking.service;

import com.flight.booking.entity.Passenger;
import com.flight.booking.entity.User;
import com.flight.booking.enums.Gender;
import com.flight.booking.model.PassengerRequest;
import com.flight.booking.model.PassengerResponse;
import com.flight.booking.repository.PassengerRepository;
import com.flight.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final UserRepository userRepository;

    @Transactional
    public PassengerResponse create(PassengerRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with id: " + req.getUserId()));

        if (req.getPassportNo() != null
                && passengerRepository.existsByPassportNo(req.getPassportNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A passenger with this passport number already exists");
        }

        Passenger p = Passenger.builder()
                .user(user)
                .passengerName(req.getPassengerName())
                .dateOfBirth(req.getDateOfBirth())
                .gender(req.getGender() == null ? null : Gender.valueOf(req.getGender()))
                .nationality(req.getNationality())
                .passportNo(req.getPassportNo())
                .passportExpiry(req.getPassportExpiry())
                .build();

        return toResponse(passengerRepository.save(p));
    }

    @Transactional(readOnly = true)
    public List<PassengerResponse> getByUser(Integer userId) {
        return passengerRepository.findByUser_UserId(userId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PassengerResponse getById(Integer id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public void delete(Integer id) {
        passengerRepository.delete(findOrThrow(id));
    }

    private Passenger findOrThrow(Integer id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Passenger not found with id: " + id));
    }

    private PassengerResponse toResponse(Passenger p) {
        return new PassengerResponse(
                p.getPassengerId(),
                p.getUser().getUserId(),
                p.getPassengerName(),
                p.getDateOfBirth(),
                p.getGender() == null ? null : p.getGender().name(),
                p.getNationality(),
                p.getPassportNo(),
                p.getPassportExpiry());
    }
}
