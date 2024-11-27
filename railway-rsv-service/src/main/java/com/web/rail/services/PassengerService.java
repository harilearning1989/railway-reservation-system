package com.web.rail.services;

import com.web.rail.dtos.PassengerRequestDto;
import com.web.rail.dtos.PassengerResponseDTO;
import jakarta.validation.Valid;

public interface PassengerService {
    PassengerResponseDTO registerPassenger(@Valid PassengerRequestDto passengerRequestDto);
}
