package com.web.rail.dtos;

import com.web.rail.enums.TrainType;

public record TrainRecord(
        Long id,
        String trainNumber,
        String trainName,
        String source,
        String destination,
        String departureTime,
        String arrivalTime,
        String duration,
        TrainType trainType,
        int totalSeats,
        double fare,
        String halts
) {
}
