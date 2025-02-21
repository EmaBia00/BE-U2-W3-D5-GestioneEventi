package com.epicode.BE_U2_W3_D5_GestioneEventi.service;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Event;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import com.epicode.BE_U2_W3_D5_GestioneEventi.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void createEvent(Event event, User organizer) {
        event.setOrganizer(organizer);
        eventRepository.save(event);
    }

    public void deleteEvent(Long eventId, User organizer) throws AccessDeniedException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (!event.getOrganizer().equals(organizer)) {
            throw new AccessDeniedException("Non hai i permessi per eliminare questo evento");
        }

        eventRepository.delete(event);
    }
}

