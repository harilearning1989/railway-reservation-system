package com.web.rail.controls;

import com.web.rail.constants.CommonConstants;
import com.web.rail.dtos.BookTicketDto;
import com.web.rail.dtos.ScheduleNewTrainDTO;
import com.web.rail.dtos.ScheduleTrainDto;
import com.web.rail.response.GlobalResponse;
import com.web.rail.response.ResponseHandler;
import com.web.rail.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                        bookTicketDto.username()), HttpStatus.BAD_REQUEST, bookTicketDto);
    }
}