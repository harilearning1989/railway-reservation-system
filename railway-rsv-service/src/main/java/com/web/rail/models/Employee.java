package com.web.rail.models;

import com.web.rail.enums.UserGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "EMPLOYEES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "EMP_ID", unique = true, nullable = false)
    private String empId;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private UserGender userGender;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "STATION")
    private String station;
    @Column(name = "DOB")
    String dob;
    @Column(name = "DOJ")
    String doj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users users; // Many-to-One relationship with User

    @PrePersist
    public void prePersist() {
        if (this.empId == null) {
            this.empId = "EMPLOYEE-" + System.currentTimeMillis();
        }
    }
}
