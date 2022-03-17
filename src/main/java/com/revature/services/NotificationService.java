package com.revature.services;

import com.revature.models.Notification;
import com.revature.models.Profile;

import java.util.List;


public interface NotificationService {

    Notification addNotification(Notification notification);
    List<Notification> findAllNotifications();
    List<Notification> findByToProfileId(Profile toProfileId);
    Notification findById(int id);
    Notification updateNotification(Notification notification);
}
