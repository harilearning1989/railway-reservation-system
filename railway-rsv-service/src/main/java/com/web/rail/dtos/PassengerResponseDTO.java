package com.web.rail.dtos;

public record PassengerResponseDTO(Long id,
                                   String passengerId,
                                   String fullName,
                                   String email,
                                   String gender,
                                   String phone,
                                   String dob,
                                   UserDTO userDTO) {
}
