package com.revature.controllers;

import com.revature.models.Profile;
import com.revature.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

//    @PostMapping("/profile")
//    @ResponseBody
//    public Profile getUserByCredential(@ResponseBody Profile profile)
//    {
//        return profileService.getProfileByCredential(profile);
//    }

    @PostMapping("/login")
    public ResponseEntity postController(@RequestBody String email, String password) {
        profileService.login(email, password);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}

