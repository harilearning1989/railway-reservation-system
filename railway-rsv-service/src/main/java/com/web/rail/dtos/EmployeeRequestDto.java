package com.web.rail.dtos;

import com.web.rail.enums.UserGender;

public record EmployeeRequestDto(String username,
                                 String password,
                                 String fullName,
                                 UserGender userGender,
                                 String email,
                                 String phone,
                                 String station,
                                 String dob,
                                 String doj) {
}
