package com.revature.controllers;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.List;
=======
import javax.servlet.http.HttpServletRequest;
>>>>>>> f5dcd467814e8794335623cd0bf377d573f3539b

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/posts")
public class PostController {

    @Autowired
    public PostService postService;

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post, HttpServletRequest req) {
        Post temp = post;
        temp.setCreator((Profile) req.getAttribute("profile"));
        temp.setPsid(SecurityUtil.getId());
        Post check = postService.addPost(temp);
        if (check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(check, HttpStatus.CREATED);
        }
    }

    @GetMapping
    @ResponseBody
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
}
