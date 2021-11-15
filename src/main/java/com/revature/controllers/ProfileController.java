package com.revature.controllers;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Profile;
import com.revature.services.ProfileService;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
@Component
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * processes login attempt via http request from client
     * @param email
     * @param password
     * @return secure token and profile as json
     */
    @PostMapping
    @NoAuthIn
    public ResponseEntity<?> login(String email, String password) {
        Profile profile = profileService.login(email,password);
        if(profile != null){
            ReturnValues rv = new ReturnValues();
            rv.setProfile(profile);
            rv.setToken(SecurityUtil.generateToken(profile));
            return new ResponseEntity<ReturnValues>(rv,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * aggregtes response from token and profile for response in login
     */
    private class ReturnValues{
        String token;
        Profile profile;
        ReturnValues(){}
        public void setToken(String token){this.token = token;}
        public void setProfile(Profile profile){this.profile = profile;}
        public String getToken(){return this.token;}
        public Profile getProfile(){return this.profile;}
    }

    /**
     * Post request that gets client profile registration info and then checks to see if information is not
     *      a duplicate in the database. If info is not a duplicate, it sets Authorization headers
     *      and calls profile service to add the request body profile to the database.
     * @PARAM Profile
     * @return aobject name profile, response header, Status code okay
     */


    @PostMapping("/profiles")
    public ResponseEntity<Profile> addNewProfile(@Valid @RequestBody Profile profile){
        System.out.println("profile" + profile);
        Profile returnedUser = profileService.getProfileByEmail(profile);
        System.out.println("returned" + returnedUser);
        if(returnedUser == null){
            HttpHeaders responseHeaders = new HttpHeaders();
            String token = SecurityUtil.generateToken(profile);
            responseHeaders.set("Authorization" , token);
            responseHeaders.set("Access-Control-Expose-Headers", "Authorization");
            return new ResponseEntity<>(profileService.addNewProfile(profile),responseHeaders, HttpStatus.CREATED);

        }
        else {
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

    }

    /**
     * Get Mapping that grabs the profile by the path variable id. It then returns the profile if it is valid.
     * @param id
     * @return Profile object with HttpStatusAccepted or HttpStatusBackRequest
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileByPid(@PathVariable("id")int id){
        Profile profile = profileService.getProfileByPid(id);
        if(profile!=null){
            return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Put mapping grabs the updated fields of profile and updates the profile in the database.
     * @param profile
     * @return Updated profile with HttpStatus.ACCEPTED otherwise if invalid returns HttpStatus.BAD_REQUEST
     */
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile){
        Profile result = profileService.updateProfile(profile);
        if(result!=null){
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
