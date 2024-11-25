package com.web.rail.controls;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resource")
public class ResourceController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin content";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee")
    public String employeeAccess() {
        return "Employee content";
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/passenger")
    public String passengerAccess() {
        return "Passenger content";
    }
}
