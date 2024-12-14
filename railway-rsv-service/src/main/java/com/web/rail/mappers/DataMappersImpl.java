package com.web.rail.mappers;

import com.web.rail.dtos.*;
import com.web.rail.models.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DataMappersImpl implements DataMappers {
    @Override
    public TrainDetails recordToEntity(TrainRecord trainRecord) {
        TrainDetails.TrainDetailsBuilder builder = TrainDetails.builder();
        builder.trainName(trainRecord.trainName());
        builder.trainNumber(trainRecord.trainNumber());
        builder.source(trainRecord.source());
        builder.destination(trainRecord.destination());
        builder.departureTime(trainRecord.departureTime());
        builder.arrivalTime(trainRecord.arrivalTime());
        builder.duration(trainRecord.duration());
        builder.trainType(trainRecord.trainType());
        builder.totalSeats(trainRecord.totalSeats());
        builder.fare(trainRecord.fare());
        builder.halts(trainRecord.halts());

        return builder.build();
    }

    @Override
    public TrainRecord entityToRecord(TrainDetails trainDetails) {
        TrainRecord trainRecord =
                new TrainRecord(trainDetails.getId(),
                        trainDetails.getTrainNumber(),
                        trainDetails.getTrainName(),
                        trainDetails.getSource(),
                        trainDetails.getDestination(),
                        trainDetails.getDepartureTime(),
                        trainDetails.getArrivalTime(),
                        trainDetails.getDuration(),
                        trainDetails.getTrainType(),
                        trainDetails.getTotalSeats(),
                        trainDetails.getFare(),
                        trainDetails.getHalts()
                );
        return trainRecord;
    }

    @Override
    public List<TrainRecord> entityListToRecordList(List<TrainDetails> trainDetailsList) {
        return Optional.ofNullable(trainDetailsList)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(this::entityToRecord)
                .toList();
    }

    @Override
    public PassengerResponseDTO toDto(Users users, Passenger passenger) {
        UserDTO userDTO = toUserDTO(users);
        return new PassengerResponseDTO(passenger.getId(),
                passenger.getPassengerId(),
                passenger.getFullName(),
                passenger.getEmail(),
                passenger.getUserGender().name(),
                passenger.getPhone(),
                passenger.getDob(),
                userDTO);
    }

    @Override
    public AdminResponseDTO toDto(Users users, Admin admin) {
        UserDTO userDTO = toUserDTO(users);
        return new AdminResponseDTO(admin.getId(),
                admin.getAdminId(),
                admin.getFullName(),
                admin.getEmail(),
                admin.getUserGender().name(),
                admin.getPhone(),
                admin.getDob(),
                userDTO);
    }

    @Override
    public Employee toEmployee(EmployeeRequestDto dto, Users users) {
        Employee.EmployeeBuilder employee = Employee.builder();
        employee.users(users);
        employee.fullName(dto.fullName());
        employee.userGender(dto.userGender());
        employee.email(dto.email());
        employee.phone(dto.phone());
        employee.station(dto.station());
        employee.dob(dto.dob());
        employee.doj(dto.doj());
        return employee.build();
    }

    @Override
    public EmployeeResponseDTO toDto(Users users, Employee employee) {
        UserDTO userDTO = toUserDTO(users);
        return new EmployeeResponseDTO(employee.getId(),
                employee.getEmpId(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getUserGender().name(),
                employee.getPhone(),
                employee.getDob(),
                userDTO);
    }

    @Override
    public Passenger toPassenger(PassengerRequestDto dto, Users users) {
        Passenger.PassengerBuilder passengerBuilder = Passenger.builder();
        passengerBuilder.users(users);
        passengerBuilder.fullName(dto.fullName());
        passengerBuilder.userGender(dto.gender());
        passengerBuilder.email(dto.email());
        passengerBuilder.phone(dto.phone());
        passengerBuilder.dob(dto.dob());

        return passengerBuilder.build();
    }

    @Override
    public Admin toAdmin(AdminRequestDto dto, Users users) {
        Admin.AdminBuilder admin = Admin.builder();
        admin.users(users);
        admin.fullName(dto.fullName());
        admin.userGender(dto.userGender());
        admin.email(dto.email());
        admin.phone(dto.phone());
        admin.station(dto.station());
        admin.dob(dto.dob());
        admin.doj(dto.doj());

        return admin.build();
    }

    private UserDTO toUserDTO(Users user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream()
                        .map(role -> new RoleDTO(role.getId(), role.getName()))
                        .toList()
        );
    }

    @Override
    public BookTicket recordToEntity(BookTicketDto dto) {
        BookTicket.BookTicketBuilder builder = BookTicket.builder();
        builder.numberOfSeats(dto.numberOfSeats());

        return builder.build();
    }

    @Override
    public List<TravelledPassenger> toEntityList(List<PassengerDto> passengers, BookTicket bookTicket) {
        return Optional.ofNullable(passengers)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(m -> {
                    TravelledPassenger.TravelledPassengerBuilder builder = TravelledPassenger.builder();
                    builder.name(m.name());
                    builder.age(m.age());
                    builder.userGender(m.gender());
                    builder.bookTicket(bookTicket);
                    return builder.build();
                }).toList();
    }
}
