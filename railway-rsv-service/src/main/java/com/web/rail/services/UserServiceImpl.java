package com.web.rail.services;

import com.web.rail.dtos.AdminRequestDto;
import com.web.rail.enums.UserGender;
import com.web.rail.models.*;
import com.web.rail.repos.*;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void registerAdmin(AdminRequestDto dto) {
        Users users = saveUsers(dto);
        Admin.AdminBuilder admin = Admin.builder();
        admin.users(users);
        admin.fullName(dto.fullName());
        admin.userGender(dto.userGender());
        admin.email(dto.email());
        admin.phone(dto.phone());
        admin.station(dto.station());
        admin.dob(dto.dob());
        admin.doj(dto.doj());
        adminRepository.save(admin.build());
    }

    private Users saveUsers(AdminRequestDto adminRequestDto) {
        Users users = new Users();
        users.setUsername(adminRequestDto.username());
        users.setPassword(passwordEncoder.encode(adminRequestDto.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        for (String roleName : adminRequestDto.roles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        }

        // Assign roles to the user
        users.setRoles(roles);
        users = userRepository.save(users);
        return users;
    }

    @Transactional
    public void registerEmployee(Users user) {
        Users savedUser = userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(savedUser);
        employeeRepository.save(employee);
    }

    @Transactional
    public void registerPassenger(Users user) {
        Users savedUser = userRepository.save(user);

        Passenger passenger = new Passenger();
        passenger.setUser(savedUser);
        passengerRepository.save(passenger);
    }
}

