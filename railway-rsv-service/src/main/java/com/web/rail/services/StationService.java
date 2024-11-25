package com.web.rail.services;

import com.web.rail.models.Station;

import java.util.List;

public interface StationService {
    List<Station> getAllStations();

    Station getStationById(Long id);

    Station createStation(Station station);

    Station updateStation(Long id, Station stationDetails);

    void deleteStation(Long id);

    Station findStationById(Long stationId);
}
