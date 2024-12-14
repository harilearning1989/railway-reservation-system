package com.web.rail.dtos;

import java.util.List;

public record BookTicketDto(
        int numberOfSeats,
        List<PassengerDto> passengers,
        long trainId,
        String username
) {
}
