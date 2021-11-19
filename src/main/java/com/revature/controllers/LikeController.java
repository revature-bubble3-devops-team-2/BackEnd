package com.revature.controllers;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;
import com.revature.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/like")
public class LikeController {

    @Autowired
    public PostService postService;

    @Autowired
    public ProfileService profileService;

    /**
     * addLike receives a request body that contains the Post that is being liked and a http servlet request that
     * contains a token, which contains the Profile liking the Post. It checks if the Like already exists and returns
     * HTTP found status when it does. Otherwise, it posts a like into the database with the Post and Profile. If it
     * is posted, it sends the HTTP ok status and HTTP bad request otherwise.
     *
     * @param post Post that is being liked
     * @param req Authorized token of the profile
     * @return HTTP ok status and the like when it is added,
     *          HTTP found request status and the like when the like already exists,
     *          HTTP bad request response and null otherwise
     */
    @PostMapping

    public ResponseEntity<Profile> addLike(@RequestBody Post post, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute("profile");
        //Profile temp = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Profile existProfile = postService.likeFindByID(temp, post);
        if (existProfile == null) { //like does not exist
            Profile check = postService.likePost(temp, post);
            if (check == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(check, HttpStatus.CREATED);
            }
        } else { //like already exists
            return new ResponseEntity<>(existProfile, HttpStatus.FOUND);
        }
    }

    /**
     * removeLike receives a request body that contains the Post that is being unliked and a http servlet request that
     * contains a token, which contains the Profile unliking the Post. When the likeService deletes a post it sends
     * back an integer whether the Like has been deleted. When it is a 1, it sends an HTTP ok status request.
     * Otherwise, it is a 0 and sends an HTTP bad request status.
     *
     * @param post Post that is being unliked
     * @param req Authorized token of the profile
     * @return HTTP ok status and null when the like has been deleted and
     *          HTTP bad request status and null otherwise
     */
    @DeleteMapping

    public ResponseEntity<Profile> removeLike(@RequestBody Post post, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute("profile");
        //Profile temp = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        int check = postService.likeDelete(temp, post);
        if (check == -1){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * getLike receives a Post that will be checked for Likes, a Boolean to let the method know to count all the Like
     * or check for one Like, and a http servlet request that contains a token, which contains the Profile unliking
     * the Post. First, the method will check if it will be counting the Likes (false) or finding a particular Like
     * (true). When counting for all the Likes, it sends a likeId to likeService.likeGet() and receives an int,
     * type-casted from long. and returns it with HTTP ok status. For finding a Like, it uses the likeID in
     * likeService.likeFindById() that returns a Like if exists and null if it doesn't. If it returns a Like, the
     * getLike method sends HTTP ok status with a 1, and HTTP ok status with a 0 if null.
     *
     * @param post Post where the Likes are being counted and found on
     * @param find Boolean whether a certain Like is be found (true) or getting the count of all the Likes
     *             within the Post (false)
     * @param req Authorized token of the profile
     * @return HTTP ok status and count of the likes,
     *          HTTP ok request status and 0 when the Like has been found, and
     *          HTTP ok request status and 1 when the Like has not been found
     */
    @GetMapping

    public ResponseEntity<Integer> getLike(@RequestHeader Post post,
                                           @RequestHeader Boolean find,
                                           HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute("profile");
        //Profile temp = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        if (!find) {
            int result = (int) postService.likeGet(temp, post);
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
