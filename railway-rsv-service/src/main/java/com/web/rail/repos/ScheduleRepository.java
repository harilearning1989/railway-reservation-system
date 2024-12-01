package com.web.rail.repos;

import com.web.rail.models.ScheduleTrain;
import com.web.rail.models.Station;
import com.web.rail.models.TrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleTrain, Long> {

    // Find schedules by departure station
    List<ScheduleTrain> findByDepartureStation(Station departureStation);

    // Find schedules by train and date (useful for specific train schedules)
    //List<ScheduleTrain> findByTrainAndDepartureTimeBetween(TrainDetails train, LocalDateTime startDate, LocalDateTime endDate);

    // Custom query to check available seats
    @Query("SELECT s FROM ScheduleTrain s WHERE s.availableSeats > 0")
    List<ScheduleTrain> findSchedulesWithAvailableSeats();

    // Corrected method using `trainDetails` as per the field name in `Schedule` entity
    boolean existsByTrainDetails_IdAndDepartureTimeBetween(Long trainId, LocalDateTime startTime, LocalDateTime endTime);

}
