package com.revature.services;

import com.revature.data.ProfileData;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.HibernateUtil;
import com.revature.utilites.SecurityUtil;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileServiceTest {


    //private static final Logger logger = LogManager.getLogger(ProfileServiceTest.class);

    @Mock
    ProfileData profileData;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setup() {
        try(Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test/resources/profile-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetExistingProfile(){
        Profile expected = new Profile(1,"test","1234","test","test","test@mail");
        assertEquals(expected, profileService.getProfileByPid(1));
    }
}
