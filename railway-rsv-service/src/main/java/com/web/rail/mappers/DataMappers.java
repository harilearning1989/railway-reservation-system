package com.web.rail.mappers;

import com.web.rail.dtos.*;
import com.web.rail.models.*;

import java.util.List;

public interface DataMappers {
    TrainDetails recordToEntity(TrainRecord trainRecord);

    TrainRecord entityToRecord(TrainDetails trainDetails);

    List<TrainRecord> entityListToRecordList(List<TrainDetails> trainDetailsList);

    PassengerResponseDTO toDto(Users users, Passenger passenger);

    Passenger toPassenger(PassengerRequestDto dto, Users users);

    Admin toAdmin(AdminRequestDto dto, Users users);

    AdminResponseDTO toDto(Users users, Admin admin);

    Employee toEmployee(EmployeeRequestDto dto, Users users);

    EmployeeResponseDTO toDto(Users users, Employee employee);

    BookTicket recordToEntity(BookTicketDto dto);

    List<TravelledPassenger> toEntityList(List<PassengerDto> passengers, BookTicket bookTicket);
}
