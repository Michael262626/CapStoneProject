package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.Points;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Points, Long> {
}
