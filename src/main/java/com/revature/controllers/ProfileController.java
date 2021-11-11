package com.revature.controllers;

import com.revature.models.Profile;
import com.revature.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ProfileController {

    @Autowired
    public ProfileService profileService;

    @PostMapping("/profiles")
    public ResponseEntity<Profile> addNewProfile(@Valid @RequestBody Profile profile){
        return new ResponseEntity<>(profileService.addNewProfile(profile), HttpStatus.CREATED);
    }

    @GetMapping("/profiles/{id}")
    public ResponseEntity<Profile> getProfileByPid(@PathVariable("id")int id){
        Profile profile = profileService.getProfileByPid(id);
        if(profile!=null){
            return new ResponseEntity<>(profile, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profiles/{id}")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile){
        Profile result = profileService.updateProfile(profile);
        if(result!=null){
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
