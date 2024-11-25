package com.web.rail.services;

import com.web.rail.dtos.TrainRecord;
import com.web.rail.exceptions.ResourceNotFoundException;
import com.web.rail.mappers.DataMappers;
import com.web.rail.models.TrainDetails;
import com.web.rail.repos.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private DataMappers dataMappers;

    @Override
    public TrainRecord registerTrain(TrainRecord trainRecord) {
        TrainDetails trainDetails = dataMappers.recordToEntity(trainRecord);
        trainDetails = trainRepository.save(trainDetails);
        trainRecord = dataMappers.entityToRecord(trainDetails);
        return trainRecord;
    }

    @Override
    public List<TrainRecord> searchTrains(String source, String destination) {
        List<TrainDetails> trainDetailsList = trainRepository.findBySourceAndDestination(source, destination);
        List<TrainRecord> trainRecordList = dataMappers.entityListToRecordList(trainDetailsList);
        return trainRecordList;
    }

    @Override
    public TrainDetails getTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train", id));
    }

    @Override
    public void deleteTrain(Long id) {
        TrainDetails train = getTrainById(id);
        trainRepository.delete(train);
    }

    @Override
    public List<TrainRecord> findAllTrains() {
        List<TrainDetails> trainDetailsList = trainRepository.findAll();
        return dataMappers.entityListToRecordList(trainDetailsList);
    }
}
