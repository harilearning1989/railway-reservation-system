package com.web.rail.controls;

import com.web.rail.dtos.AdminRequestDto;
import com.web.rail.dtos.EmployeeRequestDto;
import com.web.rail.dtos.LoginRequest;
import com.web.rail.dtos.PassengerRequestDto;
import com.web.rail.response.AuthResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.AdminService;
import com.web.rail.services.EmployeeService;
import com.web.rail.services.PassengerService;
import com.web.rail.utils.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final PassengerService passengerService;

    public AuthRestController(AuthenticationManager authenticationManager,
                              AdminService adminService,
                              EmployeeService employeeService,
                              JwtTokenProvider jwtTokenProvider, PassengerService passengerService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminService = adminService;
        this.employeeService = employeeService;
        this.passengerService = passengerService;
    }

    // Register Admin
    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        adminService.registerAdmin(adminRequestDto);
        return ResponseEntity.ok("Admin registered successfully");
    }

    // Register Employee
    @PostMapping("/register/employee")
    public ResponseEntity<String> registerEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        employeeService.registerEmployee(employeeRequestDto);
        return ResponseEntity.ok("Employee registered successfully");
    }

    // Register Passenger
    @PostMapping("/register/passenger")
    public ResponseEntity<String> registerPassenger(@Valid @RequestBody PassengerRequestDto passengerRequestDto) {
        passengerService.registerPassenger(passengerRequestDto);
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

