package com.web.rail.services;

import com.web.rail.dtos.ScheduleTrainDto;
import com.web.rail.enums.TrainStatus;
import com.web.rail.exceptions.ResourceNotFoundException;
import com.web.rail.models.ScheduleTrain;
import com.web.rail.models.Station;
import com.web.rail.models.TrainDetails;
import com.web.rail.repos.ScheduleRepository;
import com.web.rail.repos.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainRepository trainRepository;

    @Override
    public List<ScheduleTrainDto> getAllSchedules() {
        List<ScheduleTrain> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleTrainDto::from)
                .toList();
    }

    @Override
    public ScheduleTrainDto addSchedule(ScheduleTrain schedule) {
        // Set startTime to the beginning of today at 00:00:01
        LocalDateTime startTime = LocalDateTime.now().with(LocalTime.MIDNIGHT).plusSeconds(1);
        // Set endTime to exactly 24 hours from startTime
        LocalDateTime endTime = startTime.plusHours(24);
        boolean exists = scheduleRepository.existsByTrainDetails_IdAndDepartureTimeBetween(
                schedule.getTrainDetails().getId(), startTime, endTime);
        if (exists) {
            throw new IllegalArgumentException("Schedule for this train already exists at the given departure time.");
        }
        schedule = scheduleRepository.save(schedule);
        TrainDetails trainDetails = trainRepository.findById(schedule.getTrainDetails().getId())
                .orElseThrow(() -> new RuntimeException("Train not found"));
        schedule.setTrainDetails(trainDetails);
        return ScheduleTrainDto.from(schedule);
    }

    @Override
    public ScheduleTrain updateSchedule(Long id, ScheduleTrain updatedSchedule) {
        ScheduleTrain existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));

        existingSchedule.setDepartureTime(updatedSchedule.getDepartureTime());
        existingSchedule.setArrivalTime(updatedSchedule.getArrivalTime());
        existingSchedule.setAvailableSeats(updatedSchedule.getAvailableSeats());
        existingSchedule.setFare(updatedSchedule.getFare());

        return scheduleRepository.save(existingSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<ScheduleTrain> findSchedulesByStation(Station station) {
        return scheduleRepository.findByDepartureStation(station);
    }

    public List<ScheduleTrain> findSchedulesWithAvailableSeats() {
        return scheduleRepository.findSchedulesWithAvailableSeats();
    }

    @Override
    public void cancelSchedule(Long scheduleId) {
        ScheduleTrain schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setStatus(TrainStatus.CANCELED); // Update status to CANCELED
        scheduleRepository.save(schedule);
    }

    @Override
    public void markAsTraveled(Long scheduleId) {
        ScheduleTrain schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setStatus(TrainStatus.TRAVELED); // Update status to TRAVELED
        scheduleRepository.save(schedule);
    }
}
