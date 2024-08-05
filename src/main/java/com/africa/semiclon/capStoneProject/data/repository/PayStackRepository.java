package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.PaymentPayStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayStackRepository extends JpaRepository<PaymentPayStack, Long> {
}
