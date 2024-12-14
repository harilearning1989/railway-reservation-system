package com.web.rail.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "BOOK_TICKET")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMBER_OF_SEATS")
    private Integer numberOfSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_TRAIN_ID", nullable = false, referencedColumnName = "ID")
    private ScheduleNewTrain scheduleNewTrain;

    @OneToMany(mappedBy = "bookTicket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelledPassenger> travelledPassengers;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private Users users;
}
