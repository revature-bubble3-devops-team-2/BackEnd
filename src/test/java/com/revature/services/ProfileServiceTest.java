package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;


public class ProfileServiceTest {
    @Mock
    ProfileRepo profileRepo;
    @InjectMocks
    private  ProfileService profileService = new ProfileServiceImpl();

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void Hibernate() {
        try(Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test/resources/testdatabase.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addnewProfile()
   {
        Profile profile = new Profile();
        profile.setFirstName("Bob");
        profile.setLastName("Square");
        profile.setEmail("bikini@bottom.net");
        profile.setUsername("SBob");
        profile.setPasskey("secret");

        when(profileRepo.save(profile)).thenReturn(profile);
        assertEquals(profile, profileService.addNewProfile(profile));

   }
   @Test
    public void getProfileByUser()
   {
       Profile profile = new Profile(1,"user","1234","f","l","em");
//       System.out.println("profile" + profile);
//       when(profileRepo.getProfileByEmail("em")).thenReturn(profileService.getProfileByEmail(profile));
//       when(profileService.getProfileByEmail(profile)).thenReturn(profileRepo.getProfileByEmail(profile.getEmail()));
       Profile test2 = profileService.getProfileByEmail(profile);
       System.out.println("test"+test2);

       assertEquals(profile,test2);
   }

}
