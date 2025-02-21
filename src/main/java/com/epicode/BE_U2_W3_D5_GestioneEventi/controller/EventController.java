package com.epicode.BE_U2_W3_D5_GestioneEventi.controller;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Event;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import com.epicode.BE_U2_W3_D5_GestioneEventi.repository.UserRepository;
import com.epicode.BE_U2_W3_D5_GestioneEventi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public ResponseEntity<String> createEvent(@RequestBody Event event, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        eventService.createEvent(event, organizer);
        return ResponseEntity.ok("Evento creato con successo!");
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('ORGANIZZATORE')")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId, @AuthenticationPrincipal User user) throws AccessDeniedException {
        eventService.deleteEvent(eventId, user);
        return ResponseEntity.ok("Evento eliminato!");
    }
}

