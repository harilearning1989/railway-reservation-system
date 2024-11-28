package com.web.rail.controls;

import com.web.rail.constants.CommonConstants;
import com.web.rail.dtos.*;
import com.web.rail.models.Users;
import com.web.rail.repos.UserRepository;
import com.web.rail.response.AuthResponse;
import com.web.rail.response.GlobalResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.AdminService;
import com.web.rail.services.EmployeeService;
import com.web.rail.services.PassengerService;
import com.web.rail.utils.JwtTokenProvider;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final PassengerService passengerService;
    private final UserRepository userRepository;

    public AuthRestController(AuthenticationManager authenticationManager,
                              AdminService adminService,
                              EmployeeService employeeService,
                              JwtTokenProvider jwtTokenProvider,
                              PassengerService passengerService,
                              UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminService = adminService;
        this.employeeService = employeeService;
        this.passengerService = passengerService;
        this.userRepository = userRepository;
    }

    // Register Admin
    @PostMapping("/register/admin")
    public GlobalResponse registerAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        LOGGER.info("The request entered into registerAdmin with the userId::{}", adminRequestDto.username());
        AdminResponseDTO dto = adminService.registerAdmin(adminRequestDto);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.REGISTER_SUCCESS,
                        CommonConstants.USER, adminRequestDto.username()), HttpStatus.OK, dto);
    }

    // Register Employee
    @PostMapping("/register/employee")
    public GlobalResponse registerEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        LOGGER.info("The request entered into registerEmployee with the userId::{}", employeeRequestDto.username());
        EmployeeResponseDTO dto = employeeService.registerEmployee(employeeRequestDto);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.REGISTER_SUCCESS,
                        CommonConstants.USER, employeeRequestDto.username()), HttpStatus.OK, dto);
    }

    // Register Passenger
    @PostMapping("/register/passenger")
    public GlobalResponse registerPassenger(@Valid @RequestBody PassengerRequestDto passengerRequestDto) {
        LOGGER.info("The request entered into registerPassenger with the userId::{}", passengerRequestDto.username());
        /*if (userRepository.existsByUsername(signUpRequest.username())) {
            LOGGER.warn(String.format(CommonConstants.USER_NAME_ALREADY_EXISTS, signUpRequest.username()));
            return ResponseHandler.generateResponse(
                    String.format(CommonConstants.USER_NAME_ALREADY_EXISTS,
                            signUpRequest.username()), HttpStatus.BAD_REQUEST, null);
        }
        if (userRepository.existsByEmail(signUpRequest.email())) {
            LOGGER.warn(String.format(CommonConstants.USER_EMAIL_ALREADY_EXISTS, signUpRequest.email()));
            return ResponseHandler.generateResponse(
                    String.format(CommonConstants.USER_EMAIL_ALREADY_EXISTS,
                            signUpRequest.email()), HttpStatus.BAD_REQUEST, null);
        }
        if (userRepository.existsByPhone(signUpRequest.phone())) {
            LOGGER.warn(String.format(CommonConstants.USER_PHONE_ALREADY_EXISTS, signUpRequest.phone()));
            return ResponseHandler.generateResponse(
                    String.format(CommonConstants.USER_PHONE_ALREADY_EXISTS,
                            signUpRequest.phone()), HttpStatus.BAD_REQUEST, null);
        }*/
        PassengerResponseDTO dto = passengerService.registerPassenger(passengerRequestDto);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.REGISTER_SUCCESS,
                        CommonConstants.USER, passengerRequestDto.username()), HttpStatus.OK, dto);
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

    @GetMapping("/user")
    public Users getUsers() {
        long id = 42;
        return userRepository.findById(id).get();
    }

    @PostMapping("/admin")
    public AdminResponseDTO saveAdmin(@RequestBody AdminRequestDto request) {
        return adminService.saveAdmin(request);
    }
}

