package com.web.rail.dtos;

import com.web.rail.enums.UserGender;

public record PassengerRequestDto(
        String username,
        String password,
        String fullName,
        UserGender gender,
        String email,
        String phone,
        String dob
) {
}
