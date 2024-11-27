package com.web.rail.models;

import com.web.rail.enums.UserGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "PASSENGER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PASSENGER_ID", unique = true, nullable = false)
    private String passengerId;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private UserGender userGender;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "DOB")
    String dob;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false, unique = true)
    private Users users; // Many-to-One relationship with User

    @PrePersist
    public void prePersist() {
        if (this.passengerId == null) {
            this.passengerId = "PASSENGER-" + System.currentTimeMillis();
        }
    }
}
