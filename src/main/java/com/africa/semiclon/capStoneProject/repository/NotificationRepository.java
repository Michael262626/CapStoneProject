package com.africa.semiclon.capStoneProject.repository;

import com.africa.semiclon.capStoneProject.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
