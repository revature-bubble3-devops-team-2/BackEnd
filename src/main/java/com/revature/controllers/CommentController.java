package com.revature.controllers;

import com.revature.models.Comment;
import com.revature.services.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Comment>> getCommentsByPost(@RequestParam(value = "psid") Integer id){
        return new ResponseEntity<>(commentService.getCommentByPostPsid(id), HttpStatus.ACCEPTED);
    }

    /**
     * updateCommentByCid receives a request body that contains the new comment to be updated.
     * It also receives a path variable that contains the id of the comment at hand. We set the
     * comment id before updating to ensure a new id is not generated.
     *
     * @param comment Comment to be updated by user
     * @param id id identifier to get original comment.
     * @return HTTP created status and the updated comment if the updated comment is not null.
     *         Http not acceptable status and null if it is.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateCommentByCid(@RequestBody Comment comment, @PathVariable("id")int id){
        comment.setCid(id);
        Comment updatedComment = commentService.updateComment(comment);
        if(updatedComment!=null)
            return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * deleteCommentByCid receives a path variable that contains the id of the comment at hand.
     * Invokes Spring Data's abstracted delete method. If delete comment was successful, returns
     * true, false otherwise.
     *
     * @param id id identifier to get original comment
     * @return HTTP ok status and true if delete was successful.
     *         HTTP not acceptable status and false if otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCommentByCid(@PathVariable("id")int id){
        if(commentService.deleteCommentByCid(id))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
    }

}
