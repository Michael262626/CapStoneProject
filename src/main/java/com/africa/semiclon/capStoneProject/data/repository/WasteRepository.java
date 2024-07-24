package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<Waste, Long> {
}
