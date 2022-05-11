package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;
import com.revature.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.dto.PostDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.PostService;
import java.util.List;

import org.slf4j.*;

@RestController
@CrossOrigin
@RequestMapping("/bookmark")
public class BookmarkController {
    private static final String PROFILE = "profile";
    
    private static Logger log =LoggerFactory.getLogger(BookmarkController.class);
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
    public ResponseEntity<ProfileDTO> addBookmark(@RequestBody PostDTO post, HttpServletRequest req) {
        if (req.getAttribute(PROFILE) == null || post == null) {
        	log.warn("addBookmark bad request: profile or authorized token are null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {

            Profile temp = (Profile) req.getAttribute(PROFILE);
            Profile existProfile = postService.bookmarkFindByID(temp, post.toPost());
            if (existProfile == null) {
                Profile check = postService.bookmarkPost(temp, post.toPost());
                if (check == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                	log.info(String.format("Profile id: %d has bookmarked post psid: %d", check.getPid(), post.getPsid()));
                    return new ResponseEntity<>(new ProfileDTO(check), HttpStatus.CREATED);
                }
            } else {
            	
            	log.info(String.format("Profile id: %d has bookmarked post psid: %d already", existProfile.getPid(), post.getPsid()));
                return new ResponseEntity<>(new ProfileDTO(existProfile), HttpStatus.FOUND);
            }
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
    public ResponseEntity<ProfileDTO> removeBookmark(@RequestBody PostDTO post, HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute(PROFILE);
        int check = postService.bookmarkDelete(temp, post.toPost());
        if (check == -1){
        	log.warn(String.format("Profile id: %d did not have post psid: %d bookmarked", temp.getPid(), post.getPsid()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
        	
        	log.info(String.format("Profile id: %d removed from post psid: %d from bookmarks", temp.getPid(), post.getPsid()));
            return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity<List<Post>> getBookmark(HttpServletRequest req) {
        Profile temp = (Profile) req.getAttribute(PROFILE);


        List<Post> bookmarkedPosts = postService.allBookMarksByCreator(temp);

        log.info(String.format("Returned %d bookmarks", bookmarkedPosts.size()));
        return new ResponseEntity<>(bookmarkedPosts, HttpStatus.OK);
    }

    @GetMapping("/has")
    public ResponseEntity<Integer> hasBookmark(@RequestHeader int post, HttpServletRequest req) {
        PostDTO postObj = new PostDTO();
        postObj.setPsid(post);

        Profile temp = (Profile) req.getAttribute(PROFILE);

        Profile exist = postService.bookmarkFindByID(temp, postObj.toPost());
        if (exist == null) {
        	
        	log.info(String.format("Profile id: %d does not have post psid: %d bookmarked", temp.getPid(), postObj.getPsid()));
            return new ResponseEntity<>(0, HttpStatus.OK);
        } else {
        	
        	log.info(String.format("Profile id: %d has post psid: %d bookmarked", temp.getPid(), postObj.getPsid()));
            return new ResponseEntity<>(1, HttpStatus.OK);
        }

    }
}