package com.web.rail.repos;

import com.web.rail.models.ScheduleNewTrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleTrainRepo extends JpaRepository<ScheduleNewTrain, Long> {
    List<ScheduleNewTrain> findAllByScheduleAtAfter(LocalDateTime dateTime);
}
