package com.revature.controllers;

import com.revature.dto.NotificationDTO;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.dto.PostDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.PostService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/notify")

public class NotificationController {
    private static final String PROFILE = "profile";

    @Autowired
    public PostService postService;

    @PostMapping
    public ResponseEntity<NotificationDTO> addNotification(@RequestBody NotificationDTO notify, HttpServletRequest req) {
        if (req.getAttribute(PROFILE) == null || notify == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Profile temp = (Profile) req.getAttribute(PROFILE);
            Profile existProfile = postService.likeFindByID(temp, notify.toPost());
            if (existProfile == null) {
                Profile check = postService.likePost(temp, post.toPost());
                if (check == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>(new ProfileDTO(check), HttpStatus.CREATED);
                }
            } else {
                return new ResponseEntity<>(new ProfileDTO(existProfile), HttpStatus.FOUND);
            }
        }

    }
}
