package com.africa.semiclon.capStoneProject.data.repository;

import com.africa.semiclon.capStoneProject.data.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
