package com.web.rail.services;

import com.web.rail.dtos.PassengerRequestDto;
import jakarta.validation.Valid;

public interface PassengerService {
    void registerPassenger(@Valid PassengerRequestDto passengerRequestDto);
}
