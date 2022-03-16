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
@RequestMapping("/notification")

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
        //replicating frontend
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

    @GetMapping("/{toProfileId}")
    @ResponseBody
    public ResponseEntity<List<NotificationDTO>> findByToProfileId(@PathVariable Profile toProfileId) {
        List<Notification> notifications = notificationService.findByToProfileId(toProfileId);
        List<NotificationDTO> notificationDTOS = new LinkedList<>();
        notifications.forEach(notification -> notificationDTOS.add(new NotificationDTO(notification)));
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @PutMapping("/{toProfileId}/update")
    @ResponseBody
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notification, @PathVariable Profile toProfileId) {
        Notification tempN = notification.toNotification();

        //replicating frontend - to avoid "unable to join" errors in repo
        Profile fromProfile = profileService.getProfileByPid(notification.getFromProfileId().getPid());
        Profile toProfile = profileService.getProfileByPid(notification.getToProfileId().getPid());
        Post post = postService.getPostByPsid(notification.getPostId().getPsid());

        tempN.setFromProfileId(fromProfile);
        tempN.setToProfileId(toProfile);
        tempN.setPid(post);

        Notification result = notificationService.updateNotification(tempN);
        if (result != null) {
            Notification notify = new Notification();
            notify.setNid(result.getNid());
            notify.setCid(result.getCid());
            notify.setFromProfileId(result.getFromProfileId());
            notify.setToProfileId(result.getToProfileId());
            notify.setRead(result.isRead());
            return new ResponseEntity<>(new NotificationDTO(notify), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
