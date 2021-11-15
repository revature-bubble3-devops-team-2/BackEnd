package com.revature.controllers;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.services.CommentService;
import com.revature.services.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment){
        return new ResponseEntity<>(commentService.addComment(comment), HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<List<Comment>> getCommentsByPost(@RequestParam(value = "psid") Integer id){
        return new ResponseEntity<>(commentService.getCommentByPostPsid(id), HttpStatus.ACCEPTED);
    }

}
