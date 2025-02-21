package com.epicode.BE_U2_W3_D5_GestioneEventi.service;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import com.epicode.BE_U2_W3_D5_GestioneEventi.repository.UserRepository;
import com.epicode.BE_U2_W3_D5_GestioneEventi.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Registrazione completata!";
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password errata");
        }
        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }
}
