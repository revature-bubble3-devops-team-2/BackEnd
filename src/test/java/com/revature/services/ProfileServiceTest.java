package com.revature.services;
import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.HibernateUtil;
import com.revature.utilites.SecurityUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.plexus.logging.LoggerManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {

    private static final Logger logger = LogManager.getLogger(ProfileServiceTest.class);
    String pass = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4rA==jnW2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3YXsUH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS31Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPjmUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9YCYoZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9pQbtVcbqyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";

    @Mock
    ProfileRepo pr;

    @InjectMocks
    private ProfileServiceImpl psi;

    @BeforeEach
    void initMock(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setup(){
        try(Session session = HibernateUtil.getSession()){
            Transaction transaction = session.beginTransaction();
            char[] buff = new char[1400];
            int i = new FileReader("src/test/resources/profileSetup.sql").read(buff);
            if(i==0){System.exit(i);}
            session.createSQLQuery(String.valueOf(buff).trim()).executeUpdate();
            transaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    void testLoginSuccess(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(pr.findProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = psi.login("a@b.com","abc123");
        assertEquals(expected,actual);
    }

    @Test
    void testLoginNullEmail(){
        when(pr.findProfileByEmail(null)).thenReturn(null);
        Profile actual = psi.login(null,"abc123");
        assertNull(actual);
    }

    @Test
    void testLoginNullPass(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(pr.findProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = psi.login("a@b.com",null);
        assertNull(actual);
    }

    @Test
    void testLoginBadEmail(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(pr.findProfileByEmail("banana")).thenReturn(null);
        Profile actual = psi.login("banana","tomato");
        assertNull(actual);
    }

    @Test
    void testLoginBadPass(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(pr.findProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = psi.login("a@b.com","tomato");
        assertNull(actual);
    }

    @Test
    void testFindProfileByEmailSuccess(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(pr.findProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = pr.findProfileByEmail(("a@b.com"));
        assertEquals(actual,expected);
    }

    @Test
    void testFindProfileByEmailNullEntry(){
        when(pr.findProfileByEmail(null)).thenReturn(null);
        Profile actual = pr.findProfileByEmail((null));
        assertNull(actual);
    }

    @Test
    void testFindProfileByEmailSuccessBadEntry(){
        when(pr.findProfileByEmail("FloppyDisk")).thenReturn(null);
        Profile actual = pr.findProfileByEmail(("FloppyDisk"));
        assertNull(actual);
    }


}
