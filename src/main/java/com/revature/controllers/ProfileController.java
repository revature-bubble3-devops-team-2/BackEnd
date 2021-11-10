package com.revature.controllers;

import com.revature.models.Profile;
import com.revature.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<?> getUserByCredential(@ResponseBody Profile profile)
    {
        Profile returnedProfile = profileService.getProfileByCredential("", "");
        return null;
    }
}
