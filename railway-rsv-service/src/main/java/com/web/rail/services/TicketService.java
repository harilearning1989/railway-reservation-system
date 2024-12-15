package com.web.rail.services;

import com.web.rail.dtos.BookTicketDto;

import java.util.List;

public interface TicketService {
    BookTicketDto bookTicket(BookTicketDto dto);

    List<BookTicketDto> findAllBookedTickets(Long trainId);
}
