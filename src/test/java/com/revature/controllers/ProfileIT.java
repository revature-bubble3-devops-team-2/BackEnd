package com.revature.controllers;

import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

public class ProfileIT {

    private Profile profile = new Profile("username", "passkey", "name", "name", "email");
    private HttpHeaders STANDARD_HEADER = new HttpHeaders();

    @BeforeEach
    void createHttpRequestTemplate() {
        STANDARD_HEADER.clear();
        STANDARD_HEADER.set("Authorization", SecurityUtil.generateToken(profile));
    }

    @Test
    void validRegister() {

    }
}
