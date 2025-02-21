package com.epicode.BE_U2_W3_D5_GestioneEventi.repository;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Event;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizer(User organizer);
}

