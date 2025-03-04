package com.epicode.BE_U2_W3_D5_GestioneEventi.controller;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.Reservation;
import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import com.epicode.BE_U2_W3_D5_GestioneEventi.repository.UserRepository;
import com.epicode.BE_U2_W3_D5_GestioneEventi.service.ReservationService;
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
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{eventId}")
    @PreAuthorize("hasRole('UTENTE')") // ✅ Controlla che l'utente abbia il ruolo corretto
    public ResponseEntity<String> bookEvent(@PathVariable Long eventId, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        reservationService.bookEvent(eventId, user);
        return ResponseEntity.ok("Prenotazione effettuata con successo!");
    }

    @GetMapping
    @PreAuthorize("hasRole('UTENTE')")
    public List<Reservation> getUserReservations(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
        return reservationService.getUserReservations(user);
    }

    @DeleteMapping("/{reservationId}")
    @PreAuthorize("hasRole('UTENTE')")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId, @AuthenticationPrincipal UserDetails userDetails) throws AccessDeniedException {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
        reservationService.cancelReservation(reservationId, user);
        return ResponseEntity.ok("Prenotazione annullata con successo!");
    }
}

