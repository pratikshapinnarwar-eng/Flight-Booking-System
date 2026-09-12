package com.flight.booking.repository;

import com.flight.booking.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    List<Passenger> findByUser_UserId(Integer userId);

    boolean existsByPassportNo(String passportNo);
}
