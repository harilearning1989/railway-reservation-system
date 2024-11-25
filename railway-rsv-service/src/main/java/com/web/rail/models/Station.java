package com.web.rail.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    private String location;

    @Column
    private Integer platformCount;

    @Column
    private Boolean isJunction;

    @OneToMany(mappedBy = "departureStation", fetch = FetchType.LAZY)
    private List<ScheduleTrain> departureSchedules;

    @OneToMany(mappedBy = "arrivalStation", fetch = FetchType.LAZY)
    private List<ScheduleTrain> arrivalSchedules;
}
