package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.Waste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface WasteRepository extends JpaRepository<Waste, Long> {

    List<Waste> findAllByWasteCollectionDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
