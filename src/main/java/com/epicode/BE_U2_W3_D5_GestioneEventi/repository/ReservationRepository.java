package com.epicode.BE_U2_W3_D5_GestioneEventi.repository;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Event;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Reservation;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
    boolean existsByUserAndEvent(User user, Event event);
}

