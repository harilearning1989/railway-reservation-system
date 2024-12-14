package com.web.rail.repos;

import com.web.rail.models.BookTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<BookTicket, Long> {
}
