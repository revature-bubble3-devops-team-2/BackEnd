package com.revature.controllers;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/like")
public class LikeController {
    private static final String PROFILE = "profile";

    @Autowired
    public PostService postService;

    /**
     * Adds a like into the database.
     *
     * The request body must contain a post and a header with the token of the profile.
     *
     * @param post  the profile that liked the post
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *             is defined
     * @return a http response with a profile in a {@link ResponseEntity} that contains a null and bad request if
     *          like and post does not exist; a profile and created response if the like was added; a profile and
     *          found response if the like already exists
     */
    @PostMapping
    public ResponseEntity<Profile> addLike(@RequestBody Post post, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute(PROFILE);
        if (post.getCreator().equals(temp)) return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        Profile existProfile = postService.likeFindByID(temp, post);
        if (existProfile == null) {
            Profile check = postService.likePost(temp, post);
            if (check == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(check, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(existProfile, HttpStatus.FOUND);
        }
    }

    /**
     * Removes a like from the database.
     *
     * The request body must contain a post and a header with the token of the profile.
     *
     * @param post  the profile that unliked the post
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *             is defined
     * @return a http response with a profile in a {@link ResponseEntity} that contains a null and bad request if
     *          like was not deleted or a null and ok request if the like was deleted
     */
    @DeleteMapping
    public ResponseEntity<Profile> removeLike(@RequestBody Post post, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute(PROFILE);
        int check = postService.likeDelete(temp, post);
        if (check == -1){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Counts the total number of liked within a post or find if a like exists in the post.
     *
     * The request body must contain a post and a boolean, and the header must contain the token of the profile.
     *
     * @param post  a post that the likes will come from
     * @param find  a boolean that dictates whether to return the total number of likes (false) or to find a
     *              specific like (true)
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *              is defined
     * @return a http response with an integer in a {@link ResponseEntity} that contains a 0 and bad request if
     *              like was not deleted or a null and ok request if the like was deleted
     */
    @GetMapping
    public ResponseEntity<Integer> getLike(@RequestHeader Post post, @RequestHeader boolean find, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute(PROFILE);
        if (!find) {
            int result = postService.likeGet(post);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            Profile exist = postService.likeFindByID(temp, post);
            if (exist == null) {
                return new ResponseEntity<>(0, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(1, HttpStatus.OK);
            }
        }
    }
}
