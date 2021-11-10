package com.revature.services;

import com.revature.data.ProfileData;
import com.revature.models.Profile;
import com.revature.utilites.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.IOException;


public class ProfileServiceTest {
    @Mock
    ProfileData profileRepo;
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
        Profile profile = new Profile(1,"bob","pass","bob","bob","bob");

        Profile test2 =profileService.addNewProfile(profile);

        assertEquals(profile, test2);

   }

}
