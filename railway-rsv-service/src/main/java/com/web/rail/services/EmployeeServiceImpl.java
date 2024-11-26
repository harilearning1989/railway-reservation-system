package com.web.rail.services;

import com.web.rail.dtos.EmployeeRequestDto;
import com.web.rail.models.Employee;
import com.web.rail.models.Role;
import com.web.rail.models.Users;
import com.web.rail.repos.EmployeeRepository;
import com.web.rail.repos.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(UserServiceImpl userService,
                               PasswordEncoder passwordEncoder,
                               RoleRepository roleRepository,
                               EmployeeRepository employeeRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void registerEmployee(EmployeeRequestDto dto) {
        Users users = new Users();
        users.setUsername(dto.username());
        users.setPassword(passwordEncoder.encode(dto.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        String role_name = "ROLE_EMPLOYEE";
        Role role = roleRepository.findByName(role_name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(role);

        users.setRoles(roles);

        users = userService.saveUsers(users);

        Employee.EmployeeBuilder employee = Employee.builder();
        employee.users(users);
        employee.fullName(dto.fullName());
        employee.userGender(dto.userGender());
        employee.email(dto.email());
        employee.phone(dto.phone());
        employee.station(dto.station());
        employee.dob(dto.dob());
        employee.doj(dto.doj());
        employeeRepository.save(employee.build());
    }
}
