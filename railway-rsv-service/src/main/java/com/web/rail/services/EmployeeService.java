package com.web.rail.services;

import com.web.rail.dtos.EmployeeRequestDto;
import com.web.rail.dtos.EmployeeResponseDTO;

public interface EmployeeService {
    EmployeeResponseDTO registerEmployee(EmployeeRequestDto employeeRequestDto);
}
