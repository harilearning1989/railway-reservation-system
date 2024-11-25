package com.web.rail.mappers;

import com.web.rail.dtos.TrainRecord;
import com.web.rail.models.TrainDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DataMappersImpl implements DataMappers {
    @Override
    public TrainDetails recordToEntity(TrainRecord trainRecord) {
        TrainDetails.TrainDetailsBuilder builder = TrainDetails.builder();
        builder.trainName(trainRecord.trainName());
        builder.trainNumber(trainRecord.trainNumber());
        builder.source(trainRecord.source());
        builder.destination(trainRecord.destination());
        builder.departureTime(trainRecord.departureTime());
        builder.arrivalTime(trainRecord.arrivalTime());
        builder.duration(trainRecord.duration());
        builder.trainType(trainRecord.trainType());
        builder.totalSeats(trainRecord.totalSeats());
        builder.fare(trainRecord.fare());
        builder.halts(trainRecord.halts());

        return builder.build();
    }

    @Override
    public TrainRecord entityToRecord(TrainDetails trainDetails) {
        TrainRecord trainRecord =
                new TrainRecord(trainDetails.getId(),
                        trainDetails.getTrainNumber(),
                        trainDetails.getTrainName(),
                        trainDetails.getSource(),
                        trainDetails.getDestination(),
                        trainDetails.getDepartureTime(),
                        trainDetails.getArrivalTime(),
                        trainDetails.getDuration(),
                        trainDetails.getTrainType(),
                        trainDetails.getTotalSeats(),
                        trainDetails.getFare(),
                        trainDetails.getHalts()
                );
        return trainRecord;
    }

    @Override
    public List<TrainRecord> entityListToRecordList(List<TrainDetails> trainDetailsList) {
        return Optional.ofNullable(trainDetailsList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::entityToRecord)
                .toList();
    }
}
