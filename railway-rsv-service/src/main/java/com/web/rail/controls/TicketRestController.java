package com.web.rail.controls;

import com.web.rail.constants.CommonConstants;
import com.web.rail.dtos.BookTicketDto;
import com.web.rail.response.GlobalResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ticket")
public class TicketRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketRestController.class);

    private final TicketService ticketService;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("book")
    public GlobalResponse bookTicket(@RequestBody BookTicketDto dto) {
        BookTicketDto bookTicketDto = ticketService.bookTicket(dto);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.TRAIN_SCHEDULE_SUCCESS,
                        bookTicketDto.username()), HttpStatus.CREATED, bookTicketDto);
    }

    @GetMapping("findAllBookedTickets/{trainId}")
    public GlobalResponse findAllBookedTickets(@PathVariable Long trainId) {
        List<BookTicketDto> bookTicketDtos = ticketService.findAllBookedTickets(trainId);
        return ResponseHandler.generateResponse(
                String.format(CommonConstants.SUCCESSFULLY_FETCHED,
                        "Medicine"), HttpStatus.OK, bookTicketDtos);
    }
}
