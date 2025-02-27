package com.web.rail.services;

import com.web.rail.dtos.BookTicketDto;
import com.web.rail.dtos.PassengerDto;
import com.web.rail.mappers.DataMappers;
import com.web.rail.models.BookTicket;
import com.web.rail.models.ScheduleNewTrain;
import com.web.rail.models.TravelledPassenger;
import com.web.rail.models.Users;
import com.web.rail.repos.ScheduleTrainRepo;
import com.web.rail.repos.TicketRepository;
import com.web.rail.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final DataMappers dataMappers;
    private final UserRepository userRepository;
    private final ScheduleTrainRepo scheduleTrainRepo;

    public TicketServiceImpl(TicketRepository ticketRepository, DataMappers dataMappers, UserRepository userRepository, ScheduleTrainRepo scheduleTrainRepo) {
        this.ticketRepository = ticketRepository;
        this.dataMappers = dataMappers;
        this.userRepository = userRepository;
        this.scheduleTrainRepo = scheduleTrainRepo;
    }

    @Override
    public BookTicketDto bookTicket(BookTicketDto dto) {
        //dto.username()
        Users users = userRepository.findByUsername("admin")
                .orElseThrow(() -> new NoSuchElementException("User with ID " + dto.username() + " not found."));
        ScheduleNewTrain scheduleNewTrain = scheduleTrainRepo.findById(dto.trainId())
                .orElseThrow(() -> new NoSuchElementException("User with ID " + dto.username() + " not found."));
        BookTicket bookTicket = dataMappers.recordToEntity(dto);
        List<TravelledPassenger> travelledPassengers = dataMappers.toEntityList(dto.passengers(), bookTicket);
        bookTicket.setTravelledPassengers(travelledPassengers);
        bookTicket.setUsers(users);
        bookTicket.setScheduleNewTrain(scheduleNewTrain);

        bookTicket = ticketRepository.save(bookTicket);

        return dto;
    }

    @Override
    public List<BookTicketDto> findAllBookedTickets(Long trainId) {
        List<BookTicket> bookTicketList = ticketRepository.findByScheduleNewTrain_Id(trainId);
        return Optional.ofNullable(bookTicketList).orElseGet(Collections::emptyList)
                .stream()
                .map(m -> {
                    List<PassengerDto> passengerDtos = entityToDto(m.getTravelledPassengers());
                    return new BookTicketDto(m.getNumberOfSeats(), passengerDtos, trainId, "admin");
                })
                .toList();
    }

    private List<PassengerDto> entityToDto(List<TravelledPassenger> travelledPassengers) {
        return Optional.ofNullable(travelledPassengers).orElseGet(Collections::emptyList)
                .stream()
                .map(m -> new PassengerDto(m.getName(), m.getAge(), m.getUserGender()))
                .toList();
    }
}
