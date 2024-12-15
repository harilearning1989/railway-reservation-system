package com.web.rail.repos;

import com.web.rail.models.BookTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<BookTicket, Long> {

    //@Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
    //List<Employee> findEmployeesByDepartment(@Param("departmentId") Long departmentId);

    // Derived query to find employees by department
    //List<BookTicket> findAllByScheduleNewTrain(Long trainId);
    List<BookTicket> findByScheduleNewTrain_Id(Long scheduleTrainId);
}
