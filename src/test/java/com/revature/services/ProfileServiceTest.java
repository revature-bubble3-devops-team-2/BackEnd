package	com.revature.services;

import	com.revature.models.Profile;
import	com.revature.repositories.ProfileRepo;
import com.revature.services.ProfileServiceImpl;
import	com.revature.utilites.HibernateUtil;
import	com.revature.utilites.SecurityUtil;
import	java.io.FileNotFoundException;
import	java.io.FileReader;
import	java.io.IOException;
import	java.util.ArrayList;
import	java.util.List;
import	org.apache.logging.log4j.Logger;
import	org.apache.logging.log4j.LogManager;
import	org.codehaus.plexus.logging.LoggerManager;
import	org.hibernate.Session;
import	org.hibernate.Transaction;
import	org.junit.jupiter.api.*;
import	org.mockito.InjectMocks;
import	org.mockito.Mock;
import	org.mockito.MockitoAnnotations;
import	org.springframework.beans.factory.annotation.Autowired;
import	static	org.junit.jupiter.api.Assertions.*;
import	static	org.mockito.Mockito.when;


public class ProfileServiceTest {

    private static final Logger logger = LogManager.getLogger(ProfileServiceTest.class);
    String pass = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4rA==jnW2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3YXsUH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS31Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPjmUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9YCYoZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9pQbtVcbqyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";

    @Mock
    ProfileRepo profileRepo;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setup(){
        try(Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buff = new char[1400];
            int i = new FileReader("src/test/resources/profileSetup.sql").read(buff);
            if (i == 0) {
                System.exit(i);
            }
            session.createSQLQuery(String.valueOf(buff).trim()).executeUpdate();
            transaction.commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    void testLoginSuccess(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(profileRepo.getProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = profileService.login("a@b.com","abc123");
        assertEquals(expected,actual);
    }

    @Test
    void testLoginNullEmail(){
        when(profileRepo.getProfileByEmail(null)).thenReturn(null);
        Profile actual = profileService.login(null,"abc123");
        assertNull(actual);
    }

    @Test
    void testLoginNullPass(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(profileRepo.getProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = profileService.login("a@b.com",null);
        assertNull(actual);
    }

    @Test
    void testLoginBadEmail(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(profileRepo.getProfileByEmail("banana")).thenReturn(null);
        Profile actual = profileService.login("banana","tomato");
        assertNull(actual);
    }

    @Test
    void testLoginBadPass(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(profileRepo.getProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = profileService.login("a@b.com","tomato");
        assertNull(actual);
    }

    @Test
    void testFindProfileByEmailSuccess(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com");
        when(profileRepo.getProfileByEmail("a@b.com")).thenReturn(expected);
        Profile actual = profileRepo.getProfileByEmail("a@b.com");
        assertEquals(actual,expected);
    }

    @Test
    void testFindProfileByEmailNullEntry(){
        when(profileRepo.getProfileByEmail(null)).thenReturn(null);
        Profile actual = profileRepo.getProfileByEmail((null));
        assertNull(actual);
    }

    @Test
    void testFindProfileByEmailSuccessBadEntry(){
        when(profileRepo.getProfileByEmail("FloppyDisk")).thenReturn(null);
        Profile actual = profileRepo.getProfileByEmail(("FloppyDisk"));
        assertNull(actual);
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
       when(profileRepo.getProfileByEmail(profile.getEmail())).thenReturn(profile);
       Profile actual = profileService.getProfileByEmail(profile);
       assertEquals(profile,actual);
   }

}
