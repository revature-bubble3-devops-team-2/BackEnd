package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

import com.revature.models.Post;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.revature.dto.PostDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.PostService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/bookmark")
public class BookmarkController {
    private static final String PROFILE = "profile";
    private PostService ps;

    @Autowired
    public PostService postService;

    /**
     * Adds a like into the database.
     * <p>
     * The request body must contain a post and a header with the token of the profile.
     *
     * @param post the profile that liked the post
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *             is defined
     * @return a http response with a profile in a {@link ResponseEntity} that contains a null and bad request if
     * like and post does not exist; a profile and created response if the like was added; a profile and
     * found response if the like already exists
     */
    @PostMapping
    public ResponseEntity<ProfileDTO> addBookmark(@RequestBody PostDTO post, HttpServletRequest req) {
        if (req.getAttribute(PROFILE) == null || post == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {

            Profile temp = (Profile) req.getAttribute(PROFILE);
            Profile existProfile = postService.bookmarkFindByID(temp, post.toPost());
            if (existProfile == null) {
                Profile check = postService.bookmarkPost(temp, post.toPost());
                if (check == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>(new ProfileDTO(check), HttpStatus.CREATED);
                }
            } else {
                return new ResponseEntity<>(new ProfileDTO(existProfile), HttpStatus.FOUND);
            }
        }

    }

    /**
     * Removes a like from the database.
     * <p>
     * The request body must contain a post and a header with the token of the profile.
     *
     * @param post the profile that unliked the post
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *             is defined
     * @return a http response with a profile in a {@link ResponseEntity} that contains a null and bad request if
     * like was not deleted or a null and ok request if the like was deleted
     */
    @DeleteMapping
    public ResponseEntity<ProfileDTO> removeBookmark(@RequestBody PostDTO post, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute(PROFILE);
        int check = postService.bookmarkDelete(temp, post.toPost());
        if (check == -1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Counts the total number of liked within a post or find if a like exists in the post.
     * <p>
     * The request body must contain a post and a boolean, and the header must contain the token of the profile.
     *
     * @param post a post that the likes will come from
     *             //     * @param find  a boolean that dictates whether to return the total number of likes (false) or to find a
     *             specific like (true)
     * @param req  the authorized token of the profile; see {@link com.revature.aspects.AuthAspect} for how the token
     *             is defined
     * @return a http response with an integer in a {@link ResponseEntity} that contains a 0 and bad request if
     * like was not deleted or a null and ok request if the like was deleted
     */
    @GetMapping
    public ResponseEntity<Integer> getBookmark(@RequestHeader int post, HttpServletRequest req) {
        PostDTO postObj = new PostDTO();
        postObj.setPsid(post);

        Profile temp = (Profile) req.getAttribute(PROFILE);

        Profile exist = postService.bookmarkFindByID(temp, postObj.toPost());
        if (exist == null) {
            return new ResponseEntity<>(0, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(1, HttpStatus.OK);
        }

    }
}

