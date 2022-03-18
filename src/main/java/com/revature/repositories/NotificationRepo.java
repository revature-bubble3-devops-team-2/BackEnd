package com.revature.repositories;

import com.revature.models.Notification;
import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    List<Notification> findByToProfileId(Profile toProfileId);
}
