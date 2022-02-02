package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Comment;
import com.revature.services.CommentServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;

    /**
     * addComment receives a request body that contains the Comment to be added.
     *
     * @param comment to be added by the user.
     * @return HTTP created status and the comment when it is added successfully.
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment){
        return new ResponseEntity<>(commentService.addComment(comment), HttpStatus.CREATED);
    }

    /**
     * getCommentsByPost receives a Post id in the request parameter, and returns a list of comments
     * that belong to the specific post at hand.
     *
     * @param id Post id identifier to access specific object.
     * @return HTTP accepted status and a list of comments based on Post id.
     */
    
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByPost(@RequestParam(value = "psid") int id){
        return new ResponseEntity<>(commentService.getCommentsByPostPsid(id), HttpStatus.ACCEPTED);
    }

}
