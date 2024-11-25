package com.web.rail.services;

import com.web.rail.models.Station;
import com.web.rail.repos.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepository;

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public Station getStationById(Long id) {
        return stationRepository.findById(id).orElseThrow(() -> new RuntimeException("Station not found"));
    }

    @Override
    public Station createStation(Station station) {
        return stationRepository.save(station);
    }

    @Override
    public Station updateStation(Long id, Station stationDetails) {
        Station station = getStationById(id);
        station.setStationName(stationDetails.getStationName());
        station.setLocation(stationDetails.getLocation());
        station.setPlatformCount(stationDetails.getPlatformCount());
        station.setIsJunction(stationDetails.getIsJunction());
        return stationRepository.save(station);
    }

    @Override
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    @Override
    public Station findStationById(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(() -> new RuntimeException("Station not found"));
    }
}
