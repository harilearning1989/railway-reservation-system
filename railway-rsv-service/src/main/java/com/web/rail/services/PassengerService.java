package com.web.rail.services;

import com.web.rail.dtos.PassengerRequestDto;
import com.web.rail.dtos.PassengerResponseDTO;

public interface PassengerService {
    PassengerResponseDTO registerPassenger(PassengerRequestDto passengerRequestDto);
}
