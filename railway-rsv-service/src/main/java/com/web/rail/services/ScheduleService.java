package com.web.rail.services;

import com.web.rail.dtos.ScheduleNewTrainDTO;
import com.web.rail.dtos.ScheduleTrainDto;
import com.web.rail.models.ScheduleTrain;
import com.web.rail.models.Station;

import java.util.List;

public interface ScheduleService {
    List<ScheduleTrainDto> getAllSchedules();

    ScheduleTrainDto addSchedule(ScheduleTrain schedule);

    ScheduleTrain updateSchedule(Long id, ScheduleTrain updatedSchedule);

    void deleteSchedule(Long id);

    List<ScheduleTrain> findSchedulesByStation(Station station);

    List<ScheduleTrain> findSchedulesWithAvailableSeats();

    void cancelSchedule(Long id);

    void markAsTraveled(Long id);

    ScheduleTrainDto scheduleTrain(ScheduleNewTrainDTO dto,String username);

    List<ScheduleTrainDto> getAllScheduledTrains();
}
