package com.epicode.BE_U2_W3_D5_GestioneEventi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private LocalDateTime reservationDate;


    public Reservation(User user, Event event, LocalDateTime reservationDate) {
        this.user = user;
        this.event = event;
        this.reservationDate = reservationDate;
    }

}
