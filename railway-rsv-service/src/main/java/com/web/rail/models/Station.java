package com.web.rail.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "STATION_DETAILS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STATION_ID")
    private Long id;

    @Column(name = "STATION_NAME", nullable = false)
    private String stationName;

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Column
    private Integer platformCount;

    @Column
    private Boolean isJunction;

    @OneToMany(mappedBy = "departureStation", fetch = FetchType.LAZY)
    private List<ScheduleTrain> departureSchedules;

    @OneToMany(mappedBy = "arrivalStation", fetch = FetchType.LAZY)
    private List<ScheduleTrain> arrivalSchedules;

    @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
    @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
