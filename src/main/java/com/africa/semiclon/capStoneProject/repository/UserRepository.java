package com.africa.semiclon.capStoneProject.repository;

import com.africa.semiclon.capStoneProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
