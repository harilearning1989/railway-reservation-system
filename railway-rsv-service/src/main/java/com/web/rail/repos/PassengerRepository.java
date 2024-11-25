package com.web.rail.repos;

import com.web.rail.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    //Optional<Passenger> findByUsername(String username);
    //boolean existsByUsername(String username);
}
