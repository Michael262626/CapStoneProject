package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent,Long> {
    Agent findByVerificationToken(String token);

    Agent findByEmail(String email);


    Optional<Agent> findByUsername(String username);
}
