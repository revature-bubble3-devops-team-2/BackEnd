package com.revature.controllers;

import com.revature.models.Post;
import com.revature.services.PostService;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

    @Autowired
    public PostService postService;

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        Post temp = post;
        temp.setPsid(SecurityUtil.getId());
        Post check = postService.addPost(temp);
        if (check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(check, HttpStatus.CREATED);
        }
    }
}
