package com.web.rail.mappers;

import com.web.rail.dtos.TrainRecord;
import com.web.rail.models.TrainDetails;

import java.util.List;

public interface DataMappers {
    TrainDetails recordToEntity(TrainRecord trainRecord);

    TrainRecord entityToRecord(TrainDetails trainDetails);

    List<TrainRecord> entityListToRecordList(List<TrainDetails> trainDetailsList);
}
