package com.revature.repositories;

import com.revature.models.Profile;
import com.revature.services.ProfileServiceImpl;
import com.revature.services.ProfileServiceTest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProfileRepoTest {

    private static final Logger logger = LogManager.getLogger(ProfileServiceTest.class);

    @Mock
    ProfileRepo profileRepo;


    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }


    @BeforeAll
    static void setup() {
        try(Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test/resources/profile_setup.sql").read(buf);
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
        assertEquals(expected, profileRepo.getProfileByPid(1));
    }
}
