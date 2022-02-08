package com.revature.controllers;

import java.util.LinkedList;
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

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.dto.CommentDTO;
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
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO comment){
        return new ResponseEntity<>(new CommentDTO(commentService.addComment(comment.toComment())), HttpStatus.CREATED);
    }

    /**
     * getCommentsByPost receives a Post id in the request parameter, and returns a list of comments
     * that belong to the specific post at hand.
     *
     * @param id Post id identifier to access specific object.
     * @return HTTP accepted status and a list of comments based on Post id.
     */
    @GetMapping
    @NoAuthIn
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@RequestParam(value = "psid") Integer id){
    	List<Comment> comments = commentService.getCommentsByPostPsid(id);
    	List<CommentDTO> commentDtos = new LinkedList<>();
    	comments.forEach(c -> commentDtos.add(new CommentDTO(c)));
        return new ResponseEntity<>(commentDtos, HttpStatus.ACCEPTED);
    }

}
