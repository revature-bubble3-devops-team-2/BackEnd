package com.revature.services;

import com.revature.models.Notification;
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
    public Notification addNotification(Notification notify) {
        try {
            if (notify.getFromProfileId() == null || notify.getToProfileId() == null) {
                throw new NullPointerException();
            }
            notificationRepo.save(notify);
            return notify;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Notification> findAllNotifications() {
        return notificationRepo.findAll();
    }

//    @Override
//    public int NotificationDelete(int nid) {
//        return 0;
//    }

    @Override
    public String toString() {
        return super.toString();
    }
}
