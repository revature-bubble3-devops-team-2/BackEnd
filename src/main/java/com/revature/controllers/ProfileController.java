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
import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@Log4j2
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * processes login attempt via http request from client
     * @param username
     * @param password
     * @return secure token as json
     */
    @PostMapping("/login")
    @NoAuthIn
    public ResponseEntity<Profile> login(String username, String password) {
        Profile profile = profileService.login(username,password);
        if(profile != null){
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", SecurityUtil.generateToken(profile));
            return new ResponseEntity<>(profile, headers, HttpStatus.OK);
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
    @PostMapping("/register")
    @NoAuthIn
    public ResponseEntity<Profile> addNewProfile(@Valid Profile profile) {
        Profile returnedUser = profileService.getProfileByEmail(profile);
        if(returnedUser == null){
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
     * @param id
     * @return Profile object with HttpStatusAccepted or HttpStatusBackRequest
     */
    @GetMapping("{id}")
    public ResponseEntity<Profile> getProfileByPid(@PathVariable("id")int id) {
        Profile profile = profileService.getProfileByPid(id);
        if(profile!=null){
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
}
