package com.web.rail.repos;

import com.web.rail.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //Optional<Employee> findByUsername(String username);
    //boolean existsByUsername(String username);
}
