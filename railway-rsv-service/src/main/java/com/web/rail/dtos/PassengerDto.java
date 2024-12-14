package com.web.rail.dtos;

import com.web.rail.enums.UserGender;

public record PassengerDto(
        String name,
        int age,
        UserGender gender
) {
}
