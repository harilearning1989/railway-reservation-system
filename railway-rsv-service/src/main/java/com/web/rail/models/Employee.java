package com.web.rail.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "EMPLOYEES")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // Many-to-One relationship with User
}
