package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileServiceTest {


    private static final Logger logger = LogManager.getLogger(ProfileServiceTest.class);

    //private  ProfileServiceImpl profileService;

    @Mock
    ProfileRepo profileRepo;

    @InjectMocks
    private  ProfileServiceImpl profileService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExistingProfile(){
        Profile expected = new Profile(1,"test","1234","test","test","test@mail");
        when(profileRepo.getProfileByPid(1)).thenReturn(expected);
        assertEquals(expected, profileService.getProfileByPid(1));
    }

    @Test
    public void testGetInvalidProfile(){
        when(profileRepo.getProfileByPid(1)).thenReturn(null);
        assertEquals(null, profileService.getProfileByPid(1));
    }

    @Test
    public void testUpdateExistingProfile(){
        Profile expected = new Profile(1,"test","1234","updateTest","updateTest","test@mail");
        Profile old = new Profile(1,"test","1234","test","test","test@mail");
        when(profileRepo.getProfileByPid(1)).thenReturn(old);
        when(profileRepo.save(old)).thenReturn(old);
        assertEquals(expected, profileService.updateProfile(expected));
    }

    @Test
    public void testUpdateInvalidProfile(){
        Profile profile = new Profile(1,"test","1234","updateTest","updateTest","test@mail");
        when(profileRepo.getProfileByPid(1)).thenReturn(null);
        when(profileRepo.save(null)).thenReturn(null);
        assertEquals(null, profileService.updateProfile(profile));
    }
}
