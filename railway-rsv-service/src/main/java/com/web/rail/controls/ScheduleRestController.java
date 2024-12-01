package com.web.rail.controls;

import com.web.rail.constants.CommonConstants;
import com.web.rail.dtos.ScheduleTrainDto;
import com.web.rail.models.ScheduleTrain;
import com.web.rail.models.Station;
import com.web.rail.response.GlobalResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.ScheduleService;
import com.web.rail.services.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleRestController.class);
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private StationService stationService;

    @GetMapping("list")
    public GlobalResponse getAllSchedules() {
        List<ScheduleTrainDto> scheduleTrainDtoList = scheduleService.getAllSchedules();
        return ResponseHandler.generateResponse(String.format(CommonConstants.SUCCESSFULLY_FETCHED, "Schedule Train"),
                HttpStatus.OK, scheduleTrainDtoList);
    }

    @PostMapping("save")
    public GlobalResponse addSchedule(@RequestBody ScheduleTrain schedule) {
        LOGGER.info("The request entered into addSchedule with the Train Number::{}", schedule.getTrainDetails().getTrainNumber());
        LocalDateTime now = LocalDateTime.now();
        // Check if departure and arrival times are in the past
        if (schedule.getDepartureTime().isBefore(now)) {
            throw new IllegalArgumentException(CommonConstants.DEPARTURE_TIME);
        }
        if (schedule.getArrivalTime().isBefore(now)) {
            throw new IllegalArgumentException(CommonConstants.ARRIVAL_TIME);
        }
        ScheduleTrainDto scheduleTrainDto = scheduleService.addSchedule(schedule);

        return ResponseHandler.generateResponse(
                String.format(CommonConstants.TRAIN_SCHEDULE_SUCCESS,
                        schedule.getTrainDetails().getTrainName()), HttpStatus.BAD_REQUEST, scheduleTrainDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleTrain> updateSchedule(@PathVariable Long id, @RequestBody ScheduleTrain schedule) {
        ScheduleTrain updatedSchedule = scheduleService.updateSchedule(id, schedule);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search schedules by station
    @GetMapping("/station/{stationId}")
    public ResponseEntity<List<ScheduleTrain>> findSchedulesByStation(@PathVariable Long stationId) {
        Station station = stationService.findStationById(stationId);  // Assuming StationService exists
        List<ScheduleTrain> schedules = scheduleService.findSchedulesByStation(station);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Find schedules with available seats
    @GetMapping("/available")
    public ResponseEntity<List<ScheduleTrain>> findSchedulesWithAvailableSeats() {
        List<ScheduleTrain> schedules = scheduleService.findSchedulesWithAvailableSeats();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSchedule(@PathVariable Long id) {
        scheduleService.cancelSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/travel")
    public ResponseEntity<Void> markScheduleAsTraveled(@PathVariable Long id) {
        scheduleService.markAsTraveled(id);
        return ResponseEntity.noContent().build();
    }
}

