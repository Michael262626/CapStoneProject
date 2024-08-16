package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.WasteCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WasteCollectionRepository extends JpaRepository<WasteCollection, Long> {
    List<WasteCollection> findByAgentId_Id(Long agentId);

    List<WasteCollection> findByUsername(String username);
}
