package com.revature.controllers;


import com.revature.models.Info;
import com.revature.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class ProfileController {
    @Autowired
    private InfoService infoService;

    @GetMapping("/profile/{id}/info")
    public ResponseEntity<?> getInfoByProfilePid(@PathVariable("id")int id){
        Info info = infoService.getInfoByProfileId(id);
        if(info == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(info, HttpStatus.ACCEPTED);
        }
    }

    @PutMapping("/profile/{id}/info")
    @Modifying
    public ResponseEntity<?> updateInfo(@RequestBody Info info){
        if(infoService.updateInfo(info)!=null){
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/profile/{id}/info")
    public ResponseEntity<?> createInfoByProfilePid(@RequestBody Info info){
       Info returnedInfo = infoService.createInfo(info);
        if(returnedInfo!=null){
            return new ResponseEntity<>(returnedInfo, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
