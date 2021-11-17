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

@RestController
@RequestMapping("/follow")
@CrossOrigin
public class FollowController {

    @Autowired
    private ProfileService profileService;
    private SecurityUtil securityUtil;

    /**
     * Put mapping grabs the updated fields of profile and updates the profile in the database.
     * @param Authorization
     * @param FollowingUsername
     * @return Updated profile with HttpStatus.ACCEPTED otherwise if invalid returns HttpStatus.BAD_REQUEST
     */
    @PostMapping
    public ResponseEntity<Profile> newFollower(String Authorization, String FollowingUsername){
        System.out.println("Authorization: " + Authorization);
        System.out.println("FollowingUsername: " + FollowingUsername);

        Profile creator = SecurityUtil.validateToken(Authorization);
        Profile followed = profileService.getProfileByUsername(FollowingUsername);

        System.out.println("Creator: " + creator);
        System.out.println("Followed: " + followed);

        String token = SecurityUtil.generateToken(followed);
        System.out.println("Token: " + token);
        Profile Test = SecurityUtil.validateToken(token);
        System.out.println("Returned Profile from Token: " + Test);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity<Profile> deleteFollower(@RequestBody String userToken, String followingUsername){
        System.out.println("Authorization: " + userToken);
        System.out.println("FollowingUsername: " + followingUsername);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
