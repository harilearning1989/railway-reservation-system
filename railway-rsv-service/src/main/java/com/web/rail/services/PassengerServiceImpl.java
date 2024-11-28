package com.web.rail.services;

import com.web.rail.dtos.PassengerRequestDto;
import com.web.rail.dtos.PassengerResponseDTO;
import com.web.rail.mappers.DataMappers;
import com.web.rail.models.Passenger;
import com.web.rail.models.Role;
import com.web.rail.models.Users;
import com.web.rail.repos.PassengerRepository;
import com.web.rail.repos.RoleRepository;
import com.web.rail.utils.CommonUtils;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PassengerRepository passengerRepository;
    private final DataMappers dataMappers;

    public PassengerServiceImpl(UserServiceImpl userService,
                                PasswordEncoder passwordEncoder,
                                RoleRepository roleRepository, PassengerRepository passengerRepository, DataMappers dataMappers) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.passengerRepository = passengerRepository;
        this.dataMappers = dataMappers;
    }

    @Override
    @Transactional
    public PassengerResponseDTO registerPassenger(PassengerRequestDto dto) {
        Users users = new Users();
        users.setUsername(dto.username());
        String originalString = CommonUtils.decryptString(dto.password());
        users.setPassword(passwordEncoder.encode(originalString));
        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        String role_name = "ROLE_PASSENGER";
        Role role = roleRepository.findByName(role_name)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(role);
        users.setRoles(roles);
        users = userService.saveUsers(users);
        Passenger passenger = dataMappers.toPassenger(dto,users);
        passenger = passengerRepository.save(passenger);

        return dataMappers.toDto(users,passenger);
    }
}
