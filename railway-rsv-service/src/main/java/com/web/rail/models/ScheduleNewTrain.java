package com.web.rail.models;

import com.web.rail.enums.TrainStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "SCHEDULE_TRAIN")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleNewTrain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAIN_ID", nullable = false, referencedColumnName = "ID")
    private TrainDetails trainDetails;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TrainStatus status;

    @Column(name = "SCHEDULED_BY", nullable = false)
    private String scheduleBy;

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
