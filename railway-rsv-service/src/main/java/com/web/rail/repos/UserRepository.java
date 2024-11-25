package com.web.rail.repos;

import com.web.rail.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    //Users findByUsername(String username);
    Optional<Users> findByUsername(String username);
}

