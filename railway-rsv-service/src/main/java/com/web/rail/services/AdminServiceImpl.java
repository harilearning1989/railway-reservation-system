package com.web.rail.services;

import com.web.rail.dtos.AdminRequestDto;
import com.web.rail.models.Admin;
import com.web.rail.models.Role;
import com.web.rail.models.Users;
import com.web.rail.repos.AdminRepository;
import com.web.rail.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminRepository adminRepository;

    public AdminServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }
    @Override
    public void registerAdmin(AdminRequestDto dto) {
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
        adminRepository.save(admin.build());
    }
}
