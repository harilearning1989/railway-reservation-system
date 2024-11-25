package com.web.rail.models;

import com.web.rail.enums.TrainType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TRAIN_DETAILS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRAIN_NUMBER", unique = true, nullable = false)
    private String trainNumber;

    @Column(name = "TRAIN_NAME")
    private String trainName;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "DESTINATION")
    private String destination;

    @Column(name = "DEPARTURE_TIME")
    private String departureTime;

    @Column(name = "ARRIVAL_TIME")
    private String arrivalTime;

    @Column(name = "DURATION")
    private String duration;

    @Enumerated(EnumType.STRING)  // Specifies that the enum will be stored as a string in the database
    @Column(name = "TRAIN_TYPE")
    private TrainType trainType;

    @Column(name = "TOTAL_SEATS")
    private int totalSeats;

    @Column(name = "FARE")
    private double fare;

    @Column(name = "HALTS")
    private String halts;

    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "trainDetails", fetch = FetchType.LAZY)
    private List<ScheduleTrain> schedules;   // List of schedules this train operates on

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
