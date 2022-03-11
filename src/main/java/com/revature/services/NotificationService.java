package com.revature.services;

import com.revature.models.Notification;

import java.util.List;


public interface NotificationService {

    Notification addNotification(Notification notify);
    List<Notification> findAllNotifications();
//    int NotificationDelete(int pid);
}
