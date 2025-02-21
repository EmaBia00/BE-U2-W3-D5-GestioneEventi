package com.epicode.BE_U2_W3_D5_GestioneEventi.service;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Event;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Reservation;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import com.epicode.BE_U2_W3_D5_GestioneEventi.repository.EventRepository;
import com.epicode.BE_U2_W3_D5_GestioneEventi.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EventRepository eventRepository;

    public void bookEvent(Long eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (event.getAvailableSeats() <= 0) {
            throw new IllegalStateException("Posti esauriti");
        }

        if (reservationRepository.existsByUserAndEvent(user, event)) {
            throw new IllegalStateException("Hai già prenotato un posto per questo evento");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEvent(event);
        reservation.setReservationDate(LocalDateTime.now());

        event.setAvailableSeats(event.getAvailableSeats() - 1);
        reservationRepository.save(reservation);
        eventRepository.save(event);
    }

    public List<Reservation> getUserReservations(User user) {
        return reservationRepository.findByUser(user);
    }

    public void cancelReservation(Long reservationId, User user) throws AccessDeniedException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

        if (!reservation.getUser().equals(user)) {
            throw new AccessDeniedException("Non puoi cancellare una prenotazione di un altro utente");
        }

        Event event = reservation.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + 1);

        reservationRepository.delete(reservation);
        eventRepository.save(event);
    }
}

