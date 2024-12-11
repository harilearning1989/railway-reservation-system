package com.web.rail.services;

import com.web.rail.dtos.ScheduleNewTrainDTO;
import com.web.rail.dtos.ScheduleTrainDto;
import com.web.rail.enums.TrainStatus;
import com.web.rail.exceptions.ResourceNotFoundException;
import com.web.rail.models.ScheduleNewTrain;
import com.web.rail.models.ScheduleTrain;
import com.web.rail.models.Station;
import com.web.rail.models.TrainDetails;
import com.web.rail.repos.ScheduleRepository;
import com.web.rail.repos.ScheduleTrainRepo;
import com.web.rail.repos.TrainRepository;
import com.web.rail.utils.CommonUtils;
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
    @Autowired
    private ScheduleTrainRepo scheduleTrainRepo;

    @Override
    public ScheduleTrainDto scheduleTrain(ScheduleNewTrainDTO dto, String username) {
        ScheduleNewTrain.ScheduleNewTrainBuilder builder = ScheduleNewTrain.builder();
        builder.scheduleBy(username);
        builder.status(TrainStatus.SCHEDULED);
        builder.scheduleAt(CommonUtils.getLocalDateTime(dto.dateTime()));

        TrainDetails trainDetails = trainRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Train not found"));
        builder.trainDetails(trainDetails);

        ScheduleNewTrain scheduleNewTrain = builder.build();

        scheduleNewTrain = scheduleTrainRepo.save(scheduleNewTrain);

        return ScheduleTrainDto.scheduleTrain(scheduleNewTrain);
    }

    @Override
    public List<ScheduleTrainDto> getAllScheduledTrains() {
        //List<ScheduleNewTrain> schedules = scheduleTrainRepo.findAll();
        List<ScheduleNewTrain> schedules = scheduleTrainRepo.findAllByScheduleAtAfter(LocalDateTime.now());
        return schedules.stream()
                .map(ScheduleTrainDto::scheduleTrain)
                .toList();
    }

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
