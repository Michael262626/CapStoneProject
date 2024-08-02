package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.controller.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationToken,Long> {
}
