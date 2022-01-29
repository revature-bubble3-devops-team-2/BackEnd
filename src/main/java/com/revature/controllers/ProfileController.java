package com.revature.controllers;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Profile;
import com.revature.services.ProfileService;
import com.revature.utilites.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Log4j2
@RestController

@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * processes login attempt via http request from client
     *
     * @param username
     * @param password
     * @return secure token as json
     * 
     * 
     * @author marouanekhabbaz
     * refactor the code prevent it from crushing when it have an image 49 - 73
     * 
     */
    @PostMapping("/login")
    @NoAuthIn
    public ResponseEntity<Profile> login(String username, String password) {
        Profile profile = profileService.login(username, password);
        System.out.println(profile);
        if(profile != null) {
            HttpHeaders headers = new HttpHeaders();
            System.out.println(profile.getImgurl());
            System.out.println(profile);
            
            Profile pro = new Profile();
            
            pro.setPid(profile.getPid());
            pro.setUsername(profile.getUsername());
            pro.setPasskey(profile.getPasskey());
            pro.setFirstName(profile.getFirstName());
            pro.setLastName(profile.getLastName());
            pro.setEmail(profile.getEmail());
           
        
            
//            profile.setImgurl("");
            
          //  System.out.println(pro);
            
           /**
            *   int id = (int) (long) guts.get("pid");
            String username = (String) guts.get("username");
            String passkey = (String) guts.get("passkey");
            String firstName = (String) guts.get("firstName");
            String lastName = (String) guts.get("lastName");
            String email = (String) guts.get("email");
            */
            
            headers.set("Authorization", SecurityUtil.generateToken(pro));
            return new ResponseEntity<>( profile, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Post request that gets client profile registration info and then checks to see if information is not
     *      a duplicate in the database. If info is not a duplicate, it sets Authorization headers
     *      and calls profile service to add the request body profile to the database.
     * @param profile
     * @return a response with the new profile and status created
     */
    @NoAuthIn
    @PostMapping("/register")
    public ResponseEntity<Profile> addNewProfile(@Valid @RequestBody Profile profile) {
        Profile returnedUser = profileService.getProfileByEmail(profile);
        if (returnedUser == null) {
            HttpHeaders responseHeaders = new HttpHeaders();
            String token = SecurityUtil.generateToken(profile);
            responseHeaders.set("Authorization", token);
            Profile newProfile = profileService.addNewProfile(profile);
            if (newProfile == null || newProfile.isIncomplete()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(newProfile, responseHeaders, HttpStatus.CREATED);


        } else {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }
    }
    /**
     * Get Mapping that grabs the profile by the path variable id. It then returns the profile if it is valid.
     *
     * @param id
     * @return Profile object with HttpStatusAccepted or HttpStatusBackRequest
     */
    @GetMapping("{id}")
    public ResponseEntity<Profile> getProfileByPid(@PathVariable("id")int id) {
        Profile profile = profileService.getProfileByPid(id);
        if (profile!=null) {
            return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Put mapping grabs the updated fields of profile and updates the profile in the database.
     * If no token is sent in the token it fails the Auth and doesn't update the profile.
     * @param profile
     * @return Updated profile with HttpStatus.ACCEPTED otherwise if invalid returns HttpStatus.BAD_REQUEST
     */
    @PutMapping
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
        Profile result = profileService.updateProfile(profile);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adds profile to list of profiles being followed by user
     * @param email email of profile to follow
     * @param req http request including the user's authorization token in the "Authroization" header
     * @return
     */
    @PostMapping("/follow")
    public ResponseEntity<String> newFollower(String email, HttpServletRequest req) {
        Profile creator = (Profile) req.getAttribute("profile");
        Profile newProfile = profileService.addFollowerByEmail(creator, email);
        if (newProfile != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", SecurityUtil.generateToken(newProfile));
            return new ResponseEntity<>(headers, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Removed profile from list of profiles being followed by the user
     * @param email email of the profile to be unfollowed
     * @param req http request including the user's authorization token in the "Authroization" header
     * @return OK response with new authorization token, bad request response if unsuccessful
     */
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow(String email, HttpServletRequest req) {
        Profile follower = (Profile) req.getAttribute("profile");
        follower = profileService.getProfileByEmail(follower);
        if (follower != null) {
            follower = profileService.removeFollowByEmail(follower, email);
            if (follower != null) {
                log.info("Profile successfully unfollowed");
                HttpHeaders headers = new HttpHeaders();
                String newToken = SecurityUtil.generateToken(follower);
                String body = "{\"Authorization\":\"" +
                        newToken
                        + "\"}";
                return new ResponseEntity<>(body, headers, HttpStatus.ACCEPTED);
            }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
