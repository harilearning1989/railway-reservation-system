package com.web.rail.controls;

import com.web.rail.dtos.AdminRequestDto;
import com.web.rail.dtos.LoginRequest;
import com.web.rail.dtos.UserRegister;
import com.web.rail.models.Role;
import com.web.rail.models.Users;
import com.web.rail.repos.AdminRepository;
import com.web.rail.repos.EmployeeRepository;
import com.web.rail.repos.PassengerRepository;
import com.web.rail.repos.RoleRepository;
import com.web.rail.response.AuthResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.UserServiceImpl;
import com.web.rail.utils.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    private UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final PassengerRepository passengerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;

    public AuthRestController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                              AdminRepository adminRepository, EmployeeRepository employeeRepository,
                              PassengerRepository passengerRepository, JwtTokenProvider jwtTokenProvider,
                              UserServiceImpl userService,
                              RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.passengerRepository = passengerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // Register Admin
    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        // Register admin specific data
        userService.registerAdmin(adminRequestDto);
        return ResponseEntity.ok("Admin registered successfully");
    }

    // Register Employee
    @PostMapping("/employee")
    public ResponseEntity<String> registerEmployee(@Valid @RequestBody UserRegister request) {
        // Register user
        Users user = new Users();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        for (String roleName : request.roles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        }

        // Assign roles to the user
        user.setRoles(roles);

        // Register employee specific data
        userService.registerEmployee(user);
        return ResponseEntity.ok("Employee registered successfully");
    }

    // Register Passenger
    @PostMapping("/passenger")
    public ResponseEntity<String> registerPassenger(@Valid @RequestBody UserRegister request) {
        // Register user
        Users user = new Users();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        // Set roles based on user selection
        Set<Role> roles = new HashSet<>();
        for (String roleName : request.roles()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        }

        // Assign roles to the user
        user.setRoles(roles);

        // Register passenger specific data
        userService.registerPassenger(user);
        return ResponseEntity.ok("Passenger registered successfully");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Extract roles
        Set<String> roles = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toSet());
        // Generate JWT token with roles
        String token = jwtTokenProvider.generateToken(loginRequest.username(), roles);
        // Get roles from the JWT
        // String roles = jwtTokenProvider.getRolesFromJWT(jwt);

        return ResponseHandler.getAuthResponse(token, HttpStatus.OK, loginRequest.username(), roles);
    }
}

