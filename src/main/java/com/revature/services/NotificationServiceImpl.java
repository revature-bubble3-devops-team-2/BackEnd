package com.revature.services;

import com.revature.models.Notification;
import com.revature.models.Profile;
import com.revature.repositories.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    public NotificationRepo notificationRepo;

    public NotificationServiceImpl() {
        super();
    }

    @Override
    public Notification addNotification(Notification notification) {
        try {
            if (notification.getFromProfileId() == null || notification.getToProfileId() == null) {
                throw new NullPointerException();
            }
            notificationRepo.save(notification);
            return notification;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Notification> findAllNotifications() {
        return notificationRepo.findAll();
    }

    @Override
    public List<Notification> findByToProfileId(Profile toProfileId) {
        return notificationRepo.findByToProfileId(toProfileId);
    }

    @Override
    public Notification updateNotification(Notification toNotification) {
        // TODO: Wrap in try/catch
        return notificationRepo.save(toNotification);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
