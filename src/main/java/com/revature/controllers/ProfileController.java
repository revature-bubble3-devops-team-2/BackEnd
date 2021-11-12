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

@RestController
@RequestMapping("/profile")
@CrossOrigin
@Component
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

}

