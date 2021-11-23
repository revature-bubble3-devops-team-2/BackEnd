package com.revature.controllers;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {
    @NoAuthIn
    @PostMapping
    public ResponseEntity<String> getToken(@RequestBody Profile profile) {
        String token = SecurityUtil.generateToken(profile);
        log.warn(token + " from " + profile);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
