package com.web.rail.dtos;

import com.web.rail.enums.UserGender;

import java.util.List;

public record AdminRequestDto(String username,
                              String password,
                              String fullName,
                              UserGender userGender,
                              String email,
                              String phone,
                              String station,
                              String dob,
                              String doj,
                              List<String> roles) {
}
