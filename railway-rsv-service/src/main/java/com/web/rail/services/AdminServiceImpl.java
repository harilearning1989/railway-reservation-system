package com.web.rail.services;

import com.web.rail.dtos.AdminRequestDto;
import com.web.rail.dtos.AdminResponseDTO;
import com.web.rail.mappers.DataMappers;
import com.web.rail.models.Admin;
import com.web.rail.models.Role;
import com.web.rail.models.Users;
import com.web.rail.repos.AdminRepository;
import com.web.rail.repos.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final DataMappers dataMappers;

    public AdminServiceImpl(UserServiceImpl userService,
                            PasswordEncoder passwordEncoder,
                            RoleRepository roleRepository,
                            AdminRepository adminRepository, DataMappers dataMappers) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.dataMappers = dataMappers;
    }

    @Override
    @Transactional
    public AdminResponseDTO registerAdmin(AdminRequestDto dto) {
        Users users = new Users();
        users.setUsername(dto.username());
        users.setPassword(passwordEncoder.encode(dto.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        for (String roleName : dto.roles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        }

        // Assign roles to the user
        users.setRoles(roles);

        users = userService.saveUsers(users);

        Admin admin = dataMappers.toAdmin(dto, users);
        admin = adminRepository.save(admin);
        return dataMappers.toDto(users, admin);
    }

    @Override
    @Transactional
    public AdminResponseDTO saveAdmin(AdminRequestDto dto) {
        Users users = new Users();
        users.setUsername(dto.username());
        users.setPassword(passwordEncoder.encode(dto.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        for (String roleName : dto.roles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        }

        // Assign roles to the user
        users.setRoles(roles);

        users = userService.saveUsers(users);

        Admin.AdminBuilder admin = Admin.builder();
        admin.users(users);
        admin.fullName(dto.fullName());
        admin.userGender(dto.userGender());
        admin.email(dto.email());
        admin.phone(dto.phone());
        admin.station(dto.station());
        admin.dob(dto.dob());
        admin.doj(dto.doj());
        Admin savedAdmin = adminRepository.save(admin.build());
        return new AdminResponseDTO(savedAdmin.getId(),
                savedAdmin.getAdminId(),
                savedAdmin.getFullName(),
                savedAdmin.getEmail(),
                savedAdmin.getUserGender().name(),
                savedAdmin.getPhone(),
                savedAdmin.getDob(),
                null);
    }
}
