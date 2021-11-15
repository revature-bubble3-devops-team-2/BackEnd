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
        Profile temp = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        LikeId likeId = new LikeId(post, temp);
        Like check = likeService.likePost(likeId);
        if(check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(check, HttpStatus.CREATED);
        }
    }

    @DeleteMapping
    @NoAuthIn
    public ResponseEntity<Like> removeLike(@RequestBody Post post, HttpServletRequest req) {
        Profile temp = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        LikeId likeId = new LikeId(post, temp);
        int check = likeService.likeDelete(likeId);
        if (check == -1){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new Like(likeId), HttpStatus.OK);
        }
    }

    @GetMapping
    @NoAuthIn
    public ResponseEntity<Integer> getLike(@RequestBody Post post, HttpServletRequest req) {
        Profile temp = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        LikeId likeId = new LikeId(post, temp);
        int result = (int) likeService.likeGet(likeId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
