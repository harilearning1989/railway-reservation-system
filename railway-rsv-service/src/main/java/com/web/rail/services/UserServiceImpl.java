package com.web.rail.services;

import com.web.rail.exceptions.ResourceNotFoundException;
import com.web.rail.models.Users;
import com.web.rail.repos.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Users saveUsers(Users users) {
        return userRepository.save(users);
    }

    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}

