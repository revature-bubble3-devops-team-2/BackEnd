package com.revature.controllers;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @Autowired
    public PostService postService;


    /**
     * Adds a post to the database.
     *
     * The request body must contain a post and a header with the token of the profile.
     *
     * @param post  the post to be added to the database
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *          is defined
     * @return a http response with a posts in a {@link ResponseEntity} that contains a created request if the post
     *          is added, bad request otherwise
     */
    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post, HttpServletRequest req) {
        post.setCreator((Profile) req.getAttribute("profile"));
        Post check = postService.addPost(post);
        if (check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(check, HttpStatus.CREATED);
        }
    }

    /**
     * Returns a list of all the posts within the database.
     *
     * More will be added when this gets implemented more.
     *
     * @return a http response with a list of posts in a {@link ResponseEntity} that contains an ok response
     */
    @GetMapping("/page/{pageNumber}")
    @ResponseBody
    public ResponseEntity<List<Post>> getAllPostsbyPage(@PathVariable ("pageNumber") int pageNumber) {
        return new ResponseEntity<>(postService.getAllPostsPaginated(pageNumber), HttpStatus.OK);
    }

}

