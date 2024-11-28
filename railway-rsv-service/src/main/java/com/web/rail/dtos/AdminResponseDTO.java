package com.web.rail.dtos;

public record AdminResponseDTO(Long id,
                               String adminId,
                               String fullName,
                               String email,
                               String gender,
                               String phone,
                               String dob,
                               UserDTO userDTO) {
}
