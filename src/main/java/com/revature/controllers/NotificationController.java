package com.revature.controllers;

import com.revature.dto.NotificationDTO;
import com.revature.models.Notification;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;
import com.revature.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.revature.services.NotificationService;
import javax.servlet.http.HttpServletRequest;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notify")

public class NotificationController {
    private static final String PROFILE = "profile";

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public ProfileService profileService;

    @Autowired
    public PostService postService;

    @PostMapping
    public ResponseEntity<NotificationDTO> addNotification(@RequestBody NotificationDTO notificationDTO, HttpServletRequest req) {

        Notification newNotification = notificationDTO.toNotification();

        Profile fromProfile = profileService.getProfileByPid(notificationDTO.getFromProfileId().getPid());
        Profile toProfile = profileService.getProfileByPid(notificationDTO.getToProfileId().getPid());
        Post post = postService.getPostByPsid(notificationDTO.getPostId().getPsid());

        newNotification.setFromProfileId(fromProfile);
        newNotification.setToProfileId(toProfile);
        newNotification.setPid(post);

        Notification check = notificationService.addNotification(newNotification);

        if (check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new NotificationDTO(check), HttpStatus.CREATED);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<NotificationDTO>> findAllNotifications() {
        System.out.println("Notification err");
        List<Notification> notifications = notificationService.findAllNotifications();
        List<NotificationDTO> notificationDTOS = new LinkedList<>();
        System.out.println("HERE" + notifications);

        notifications.forEach(notify -> notificationDTOS.add(new NotificationDTO(notify)));
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }
}
