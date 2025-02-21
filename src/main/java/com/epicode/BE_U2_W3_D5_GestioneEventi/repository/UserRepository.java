package com.epicode.BE_U2_W3_D5_GestioneEventi.repository;

import com.epicode.BE_U2_W3_D5_GestioneEventi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

