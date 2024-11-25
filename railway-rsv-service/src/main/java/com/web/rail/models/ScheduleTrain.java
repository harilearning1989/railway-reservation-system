package com.web.rail.models;

import com.web.rail.enums.TrainStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "SCHEDULE_DETAILS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false, referencedColumnName = "id")
    private TrainDetails trainDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id", nullable = false, referencedColumnName = "id")
    private Station departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_station_id", nullable = false, referencedColumnName = "id")
    private Station arrivalStation;

    @Column(name = "DEPARTURE_TIME")
    private LocalDateTime departureTime;
    @Column(name = "ARRIVAL_TIME")
    private LocalDateTime arrivalTime;
    @Column(name = "AVAILABLE_SEATS")
    private int availableSeats;  // Manage seat availability in this schedule
    @Column(name = "FARE")
    private double fare;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING) // Using an enum for status
    private TrainStatus status; // Train status

}
