package com.web.rail.controls;

import com.web.rail.constants.CommonConstants;
import com.web.rail.dtos.TrainRecord;
import com.web.rail.models.TrainDetails;
import com.web.rail.response.GlobalResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
@CrossOrigin(origins = "*")
public class TrainRestController {

    @Autowired
    private TrainService trainService;

    @GetMapping("findAll")
    public GlobalResponse findAllTrains() {
        List<TrainRecord> trains = trainService.findAllTrains();
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.SUCCESSFULLY_FETCHED,
                        "Medicine"), HttpStatus.OK, trains);
    }

    @PostMapping("/register")
    public GlobalResponse registerTrain(@RequestBody TrainRecord trainRecord) {
        trainRecord = trainService.registerTrain(trainRecord);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.TRAIN_REGISTER_SUCCESS,
                        CommonConstants.TRAIN, trainRecord.trainName()), HttpStatus.OK, trainRecord);
    }

    @GetMapping("/search")
    public GlobalResponse searchTrains(@RequestParam String source, @RequestParam String destination) {
        List<TrainRecord> trains = trainService.searchTrains(source, destination);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.SUCCESSFULLY_FETCHED,
                        "Medicine"), HttpStatus.OK, trains);

    }

    @GetMapping("/{id}")
    public TrainDetails getTrainById(@PathVariable Long id) {
        return trainService.getTrainById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        trainService.deleteTrain(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
