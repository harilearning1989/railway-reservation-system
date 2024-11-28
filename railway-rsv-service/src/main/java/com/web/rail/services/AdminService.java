package com.web.rail.services;

import com.web.rail.dtos.AdminRequestDto;
import com.web.rail.dtos.AdminResponseDTO;

public interface AdminService {
    AdminResponseDTO registerAdmin(AdminRequestDto adminRequestDto);

    AdminResponseDTO saveAdmin(AdminRequestDto request);
}
