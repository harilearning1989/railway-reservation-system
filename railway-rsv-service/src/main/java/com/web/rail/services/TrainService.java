package com.web.rail.services;

import com.web.rail.dtos.TrainRecord;
import com.web.rail.models.TrainDetails;

import java.util.List;

public interface TrainService {
    TrainRecord registerTrain(TrainRecord trainRecord);

    List<TrainRecord> searchTrains(String source, String destination);

    TrainDetails getTrainById(Long id);

    void deleteTrain(Long id);

    List<TrainRecord> findAllTrains();
}
