package com.revature.controllers;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Profile;
import com.revature.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

//    @PostMapping("/profile")
//    public Profile getUserByCredential(@RequestBody Profile profile)
//    {
//        return profileService.getProfileByCredential(profile);
//    }

    @PostMapping
    @NoAuthIn
    public ResponseEntity<Profile> login(String email, String password) {
        Profile p = profileService.login(email, password);
        return new ResponseEntity<>(p,HttpStatus.OK);
//        return new ResponseEntity<>(profileService.login(email,password),HttpStatus.OK);
    }


//if(userService.login(email,password) != null){
//        return new ResponseEntity<>(userService.login(email,password),HttpStatus.OK);
//    }
//        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//}

}

