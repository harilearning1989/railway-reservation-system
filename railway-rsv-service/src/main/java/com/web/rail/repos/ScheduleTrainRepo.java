package com.web.rail.repos;

import com.web.rail.models.ScheduleNewTrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTrainRepo extends JpaRepository<ScheduleNewTrain, Long> {
}
