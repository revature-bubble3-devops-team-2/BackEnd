package com.revature.services;

import com.revature.controllers.ProfileController;
import com.revature.models.Notification;
import com.revature.models.Profile;
import com.revature.repositories.NotificationRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
	private static Logger log =LoggerFactory.getLogger(NotificationServiceImpl.class);
	
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
        	log.error("NotificationServiceImpl.addNotification: {}",e.getMessage());
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
    public Notification updateNotification(Notification notification) {
        notificationRepo.save(notification);
        return notification;
    }

    @Override
    public Notification findById(int id) {
        Optional<Notification> notification = notificationRepo.findById(id);
        return notification.orElse(null);
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
