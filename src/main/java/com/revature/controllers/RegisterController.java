package com.revature.controllers;

import com.revature.models.User;
import com.revature.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/register")
public class RegisterController {
    private Logger logger = Logger.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("birthday") String birthdayString) throws NoSuchAlgorithmException, InvalidKeyException, ParseException {
        Date birthday = DateFormat.getDateInstance(DateFormat.LONG).parse(birthdayString);
        User user = new User(email, firstName, lastName, birthday);

        return new ResponseEntity<>(userService.register(user, password), HttpStatus.CREATED);
    }
}
