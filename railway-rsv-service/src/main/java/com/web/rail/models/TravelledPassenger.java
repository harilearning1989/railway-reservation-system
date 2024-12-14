package com.web.rail.models;

import com.web.rail.enums.UserGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRAVELLED_PASSENGER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelledPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME", length = 50)
    private String name;
    @Column(name = "AGE")
    private int age;
    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private UserGender userGender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_TICKET_ID", nullable = false, referencedColumnName = "ID")
    private BookTicket bookTicket;
}
