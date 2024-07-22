package com.africa.semiclon.capStoneProject.repository;

import com.africa.semiclon.capStoneProject.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
