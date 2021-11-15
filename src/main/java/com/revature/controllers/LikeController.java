package com.revature.controllers;


import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/like")
public class LikeController {

    @Autowired
    public LikeService likeService;

    @PostMapping
    @NoAuthIn
    public ResponseEntity<Like> addLike(@RequestBody Post post, HttpServletRequest req) {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");

        LikeId likeId = new LikeId(post, tempProfile);
        //Like check = likeService.likePost(post, (Profile) req.getAttribute("creator"));
        Like check = likeService.likePost(likeId);
        if(check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(check, HttpStatus.CREATED);
        }
    }
}
