package com.web.rail.dtos;

import com.web.rail.models.Role;

import java.util.Set;

public record PassengerResponseDTO(Long id,
                                   String username,
                                   Set<Role> roles,
                                   String passengerId,
                                   String fullName,
                                   String email,
                                   String gender,
                                   String phone,
                                   String dob) {
}
