package com.web.rail.services;

import com.web.rail.dtos.PassengerRequestDto;
import com.web.rail.models.Passenger;
import com.web.rail.models.Role;
import com.web.rail.models.Users;
import com.web.rail.repos.PassengerRepository;
import com.web.rail.repos.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(UserServiceImpl userService,
                                PasswordEncoder passwordEncoder,
                                RoleRepository roleRepository, PassengerRepository passengerRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void registerPassenger(PassengerRequestDto dto) {
        Users users = new Users();
        users.setUsername(dto.username());
        users.setPassword(passwordEncoder.encode(dto.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        String role_name = "ROLE_PASSENGER";
        Role role = roleRepository.findByName(role_name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(role);

        users.setRoles(roles);

        users = userService.saveUsers(users);

        Passenger.PassengerBuilder passenger = Passenger.builder();
        passenger.users(users);
        passenger.fullName(dto.fullName());
        passenger.userGender(dto.userGender());
        passenger.email(dto.email());
        passenger.phone(dto.phone());
        passenger.dob(dto.dob());
        passengerRepository.save(passenger.build());
    }
}
