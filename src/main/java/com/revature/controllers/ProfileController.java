package com.revature.controllers;

import com.revature.models.Profile;
import com.revature.services.ProfileService;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class ProfileController {
    private SecurityUtil securityUtil = new SecurityUtil();

    @Autowired
    private ProfileService profileService;

    @PostMapping("/profiles")
    @ResponseBody
    public ResponseEntity<Profile> addNewProfile(@Valid @RequestBody Profile profile){

        Profile returnedUser = profileService.getProfileByEmail(profile);
        if(returnedUser.getEmail() == profile.getEmail()){
            return new ResponseEntity<>(HttpStatus.IM_USED);
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        String token = SecurityUtil.generateToken(profile);
        responseHeaders.set("Authorization" , token);
        responseHeaders.set("Access-Control-Expose-Headers", "Authorization");
        return new ResponseEntity<>(profileService.addNewProfile(profile),responseHeaders, HttpStatus.CREATED);

    }
}
