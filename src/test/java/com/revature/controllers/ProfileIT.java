package com.revature.controllers;

import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileIT {
    private static final String BASE_URL = "http://localhost:8082/profile";
    private static final RestTemplate restTemplate = new RestTemplate();
    private Profile profile = new Profile("username", SecurityUtil.hashPassword("passkey"), "name", "name", "email");
    private HttpHeaders stdHeader = new HttpHeaders();

    @BeforeEach
    void createHttpRequestTemplate() {
        stdHeader.clear();
        stdHeader.set("Authorization", restTemplate.postForEntity("http://localhost:8082/test", profile, String.class).getBody());
    }

    @Test
    void validRegister() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("password", profile.getPasskey());
        HttpEntity<Profile> req = new HttpEntity<>(profile);
        ResponseEntity<Profile> response = restTemplate.postForEntity(BASE_URL+"/register", req, Profile.class);
        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertNotNull(response.getHeaders()),
                () -> assertNotNull(SecurityUtil.validateToken(response.getHeaders().get("Authorization").get(0)))
        );
    }
}
