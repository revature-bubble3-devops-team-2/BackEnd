package com.revature.controllers;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/posts")
public class PostController {

    @Autowired
    public PostService postService;
    /**
     * @param post Post to be added to the database
     * @param req Authorized token of the profile
     * @return HTTP created status and the original post when it is added,
     *          HTTP bad request status and null otherwise
     */
    @PostMapping
    @NoAuthIn
    public ResponseEntity<Post> addPost(@RequestBody Post post, HttpServletRequest req) {
        Post temp = post;
        //temp.setCreator((Profile) req.getAttribute("creator"));
        temp.setPsid(SecurityUtil.getId());
        Post check = postService.addPost(temp);
        if (check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(check, HttpStatus.CREATED);
        }
    }

    /**
     * @return list of all the Posts
     */
    @GetMapping
    @ResponseBody
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
}
