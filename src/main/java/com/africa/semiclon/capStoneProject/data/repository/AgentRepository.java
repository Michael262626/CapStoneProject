package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent,Long> {
    Agent findByVerificationToken(String token);
}
