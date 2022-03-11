package com.revature.repositories;

import com.revature.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepo extends JpaRepository<Notification, Integer> {
}
