package com.web.rail.services;

import com.web.rail.models.Users;
import com.web.rail.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new User(
                users.getUsername(),
                users.getPassword(),
                users.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toSet())
        );
        /*if (adminRepository.findByUsername(username).isPresent()) {
            Admin admin = adminRepository.findByUsername(username).get();
            return new User(admin.getUsername(), admin.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else if (employeeRepository.findByUsername(username).isPresent()) {
            Employee employee = employeeRepository.findByUsername(username).get();
            return new User(employee.getUsername(), employee.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        } else if (passengerRepository.findByUsername(username).isPresent()) {
            Passenger passenger = passengerRepository.findByUsername(username).get();
            return new User(passenger.getUsername(), passenger.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_PASSENGER")));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }*/
    }
}