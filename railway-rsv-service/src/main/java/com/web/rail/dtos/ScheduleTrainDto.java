package com.web.rail.dtos;

import com.web.rail.enums.TrainType;
import com.web.rail.models.ScheduleTrain;

import java.time.LocalDateTime;

public record ScheduleTrainDto(Long id,
                               String trainNumber,
                               String trainName,
                               String departureStation,
                               String arrivalStation,
                               LocalDateTime departureTime,
                               LocalDateTime arrivalTime,
                               Integer availableSeats,
                               String halts,
                               double fare,
                               String source,
                               String destination,
                               TrainType trainType,
                               int totalSeats,
                               String status) {
    public static ScheduleTrainDto from(ScheduleTrain schedule) {
        return new ScheduleTrainDto(
                schedule.getId(),
                schedule.getTrainDetails().getTrainNumber(),
                schedule.getTrainDetails().getTrainName(),
                schedule.getDepartureStation().getStationName(),
                schedule.getArrivalStation().getStationName(),
                schedule.getDepartureTime(),
                schedule.getArrivalTime(),
                schedule.getAvailableSeats(),
                schedule.getTrainDetails().getHalts(),
                schedule.getFare(),
                schedule.getTrainDetails().getSource(),
                schedule.getTrainDetails().getDestination(),
                schedule.getTrainDetails().getTrainType(),
                schedule.getTrainDetails().getTotalSeats(),
                schedule.getStatus().name()
        );
    }
}