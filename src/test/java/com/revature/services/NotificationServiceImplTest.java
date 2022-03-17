package com.revature.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.*;
import com.revature.repositories.NotificationRepo;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class NotificationServiceImplTest {
    private static final int ID = 1;
    private static final boolean READ = false;

    private static final String NAME = "testName";
    private static final String EMAIL = "test@email.com";
    private static final String USERNAME = "testUsername";
    private static final String PASSWORD = "testPassword";
    private static final boolean VERIFICATION = true;

    private Comment comment = new Comment();
    private Profile fromProfile = new Profile();
    private Profile toProfile = new Profile();
    private Notification notification = new Notification();

    private static final String BODY = "Body text.";
    private static final String IMAGE = "https://imageurl.org";
    private Post post = new Post();

    private Timestamp getTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    @Mock
    NotificationRepo notificationRepo;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        fromProfile = new Profile(USERNAME, PASSWORD, NAME, NAME, EMAIL, VERIFICATION);
        toProfile = new Profile(USERNAME, PASSWORD, NAME, NAME, EMAIL, VERIFICATION);
        post = new Post(fromProfile, BODY, IMAGE, getTime(), null);
        notification = new Notification(ID, READ, comment, fromProfile, toProfile, post);
    }

    @Test
    void testAddNotification() {
        assertNotNull(notificationService.addNotification(notification));
    }

    @Test
    void testFindAllNotifications() {
        List<Notification> expectedNotifications = new ArrayList<>();

        expectedNotifications.add(notification);
        expectedNotifications.add(notification);
        expectedNotifications.add(notification);

        notificationService.addNotification(expectedNotifications.get(0));
        notificationService.addNotification(expectedNotifications.get(1));
        notificationService.addNotification(expectedNotifications.get(2));

        when(notificationRepo.findAll()).thenReturn(expectedNotifications);

        List<Notification> actualNotifications = notificationService.findAllNotifications();

        assertAll(
                () -> assertNotNull(actualNotifications),
                () -> assertEquals(expectedNotifications.size(), actualNotifications.size()),
                () -> assertEquals(expectedNotifications.get(0), actualNotifications.get(0)),
                () -> assertEquals(expectedNotifications.get(1), actualNotifications.get(1)),
                () -> assertEquals(expectedNotifications.get(2), actualNotifications.get(2))
        );
    }

    @Test
    void testFindByToProfileId() {
        List<Notification> expectedNotifications = new ArrayList<>();
        expectedNotifications.add(notification);

        notificationService.addNotification(expectedNotifications.get(0));

        when(notificationRepo.findByToProfileId(fromProfile)).thenReturn(expectedNotifications);

        List<Notification> actualNotifications = notificationService.findByToProfileId(fromProfile);

        assertAll(
                () -> assertNotNull(actualNotifications),
                () -> assertEquals(expectedNotifications.size(), actualNotifications.size()),
                () -> assertEquals(expectedNotifications.get(0), actualNotifications.get(0))
        );
    }

    @Test
    void testUpdateNotification() {
        notification.setRead(true);
        when(notificationRepo.save(notification)).thenReturn(notification);
        assertEquals(notification, notificationService.updateNotification(notification));
    }
}