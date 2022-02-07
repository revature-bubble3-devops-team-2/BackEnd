package com.revature.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.dto.PostDTO;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;

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
    public ResponseEntity<PostDTO> addPost(@RequestBody PostDTO post, HttpServletRequest req) {
    	System.out.println(post);
    	Post newPost = post.toPost();
        newPost.setCreator((Profile) req.getAttribute("profile"));
        Post check = postService.addPost(newPost);
        if (check == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new PostDTO(check), HttpStatus.CREATED);
        }
    }
    /**
     * Returns a list of all the posts within the database.
     *
     * More will be added when this gets implemented more.
     *
     * @param pageNumber
     * @return a http response with a list of posts in a {@link ResponseEntity} that contains an ok response
     */
    @GetMapping("/page/{pageNumber}")
    @ResponseBody
    public ResponseEntity<List<PostDTO>> getAllPostsbyPage(@PathVariable ("pageNumber") int pageNumber) {
    	List<Post> posts = postService.getAllPostsPaginated(pageNumber);
    	List<PostDTO> postDtos = new LinkedList<>();
    	posts.forEach(p -> postDtos.add(new PostDTO(p)));
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    
	@NoAuthIn
    @GetMapping("/page/all")
    @ResponseBody
    public ResponseEntity<List<PostDTO>> getAllPostsbyPage() {
    	List<Post> posts = postService.getAllPosts();
    	List<PostDTO> postDtos = new LinkedList<>();
    	posts.forEach(p -> postDtos.add(new PostDTO(p)));
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    
    
    
}


