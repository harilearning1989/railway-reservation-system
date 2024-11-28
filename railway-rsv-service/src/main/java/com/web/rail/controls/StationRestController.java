package com.web.rail.controls;

import com.web.rail.constants.CommonConstants;
import com.web.rail.models.Station;
import com.web.rail.response.GlobalResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
public class StationRestController {

    private final StationService stationService;

    public StationRestController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("findAll")
    public GlobalResponse getAllStations() {
        List<Station> stationList = stationService.getAllStations();
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.SUCCESSFULLY_FETCHED,
                        "Medicine"), HttpStatus.OK, stationList);
    }

    @GetMapping("/{id}")
    public Station getStationById(@PathVariable Long id) {
        return stationService.getStationById(id);
    }

    @PostMapping
    public Station createStation(@RequestBody Station station) {
        return stationService.createStation(station);
    }

    @PutMapping("/{id}")
    public Station updateStation(@PathVariable Long id, @RequestBody Station stationDetails) {
        return stationService.updateStation(id, stationDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
    }
}

