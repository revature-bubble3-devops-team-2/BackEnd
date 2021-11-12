//package com.revature.controllers;
//
//
//import com.revature.models.Post;
//import com.revature.models.Profile;
//import com.revature.repositories.PostRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
//public class PostController {
//
//    @Autowired
//    PostRepo postRepo;
//    @Autowired
//    Post post;
//    @Autowired
//    Profile profile;
//
//    @GetMapping("/post")
//    public ResponseEntity<List<Post>> getAllPost() {
//        return new ResponseEntity<>(postRepo.getall(), HttpStatus.OK);
//    }
//}

