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
import javax.validation.constraints.Null;

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

        Notification newNotification = notificationDTO.toNewNotification();

        Profile fromProfile = profileService.getProfileByPid(notificationDTO.getFromProfileId().getPid());
        Profile toProfile = profileService.getProfileByPid(notificationDTO.getToProfileId().getPid());
        Post post;


        try {
            post = postService.getPostByPsid(notificationDTO.getPostId().getPsid());
        } catch(NullPointerException e) {
            post = null;
        }

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

    @PutMapping("/{nid}/update-read")
    @ResponseBody
    public ResponseEntity<NotificationDTO> setReadNotification(@PathVariable int nid, @RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationService.findById(nid);
        notification.setRead(notificationDTO.isRead());

        Notification savedNotification = notificationService.updateNotification(notification);

        NotificationDTO responseDTO = new NotificationDTO(savedNotification);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
