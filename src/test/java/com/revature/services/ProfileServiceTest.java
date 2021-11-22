package com.revature.services;

<<<<<<< HEAD
import	com.revature.models.Profile;
import	com.revature.repositories.ProfileRepo;
import	org.junit.jupiter.api.*;
import	org.mockito.InjectMocks;
import	org.mockito.Mock;
import	org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import	static	org.junit.jupiter.api.Assertions.*;
import	static	org.mockito.Mockito.when;

public class ProfileServiceTest {
    private final String pass = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4" +
            "rA==jnW2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3" +
            "YXsUH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS31" +
            "Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22qCVCR" +
            "2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPjmUkq1nQxu" +
            "x1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9YCYoZcKm9vrrH+" +
            "CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9pQbtVcbqyJGNRFA6O" +
            "AGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";
    List<Profile> pList = new ArrayList<>();
=======
import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {
    private static final String USERNAME = "dummyUsername";
    private static final String PASSWORD = "abc123";
    private static final String EMAIL = "dummy@email.com";
    private Profile expected = new Profile();
>>>>>>> main

    @Mock
    ProfileRepo profileRepo;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        String name = "dummyName";
        String passkey = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4rA==jnW" +
                "2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3YXs" +
                "UH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS3" +
                "1Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22" +
                "qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPj" +
                "mUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9Y" +
                "CYoZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9" +
                "pQbtVcbqyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";
        expected = new Profile(USERNAME, passkey, name, name, EMAIL);
    }

    @Test
    void testLoginSuccess(){
<<<<<<< HEAD
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com", pList);
        when(profileRepo.getProfileByUsername("joey")).thenReturn(expected);
        Profile actual = profileService.login("joey","abc123");
=======
        when(profileRepo.getProfileByUsername(USERNAME)).thenReturn(expected);
        Profile actual = profileService.login(USERNAME, PASSWORD);
>>>>>>> main
        assertEquals(expected,actual);
    }

    @Test
<<<<<<< HEAD
    void testLoginNullUsername(){
        when(profileRepo.getProfileByUsername(null)).thenReturn(null);
        Profile actual = profileService.login(null,"abc123");
=======
    void testLoginNullEmail(){
        when(profileRepo.getProfileByEmail(null)).thenReturn(null);
        Profile actual = profileService.login(null, PASSWORD);
>>>>>>> main
        assertNull(actual);
    }

    @Test
    void testLoginNullPass(){
<<<<<<< HEAD
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com", pList);
        when(profileRepo.getProfileByUsername("joey")).thenReturn(expected);
        Profile actual = profileService.login("a@b.com",null);
=======
        when(profileRepo.getProfileByEmail(EMAIL)).thenReturn(expected);
        Profile actual = profileService.login(EMAIL,null);
>>>>>>> main
        assertNull(actual);
    }

    @Test
<<<<<<< HEAD
    void testLoginBadUsername(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com", pList);
        when(profileRepo.getProfileByUsername("banana")).thenReturn(null);
=======
    void testLoginBadEmail(){
        when(profileRepo.getProfileByEmail("banana")).thenReturn(null);
>>>>>>> main
        Profile actual = profileService.login("banana","tomato");
        assertNull(actual);
    }

    @Test
    void testLoginBadPass(){
<<<<<<< HEAD
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com", pList);
        when(profileRepo.getProfileByUsername("a@b.com")).thenReturn(expected);
        Profile actual = profileService.login("a@b.com","tomato");
=======
        when(profileRepo.getProfileByEmail(EMAIL)).thenReturn(expected);
        Profile actual = profileService.login(EMAIL,"tomato");
>>>>>>> main
        assertNull(actual);
    }

    @Test
<<<<<<< HEAD
    void testFindProfileByUsernameSuccess(){
        Profile expected = new Profile(12, "joey", pass, "Joe", "Seph", "a@b.com", pList);
        when(profileRepo.getProfileByUsername("joey")).thenReturn(expected);
        Profile actual = profileRepo.getProfileByUsername("joey");
=======
    void testFindProfileByEmailSuccess(){
        when(profileRepo.getProfileByEmail(EMAIL)).thenReturn(expected);
        Profile actual = profileRepo.getProfileByEmail(EMAIL);
>>>>>>> main
        assertEquals(actual,expected);
    }

    @Test
    void testFindProfileByUsernameNullEntry(){
        when(profileRepo.getProfileByEmail(null)).thenReturn(null);
        Profile actual = profileRepo.getProfileByEmail((null));
        assertNull(actual);
    }

    @Test
    void testFindProfileByUsernameBadEntry(){
        when(profileRepo.getProfileByEmail("FloppyDisk")).thenReturn(null);
        Profile actual = profileRepo.getProfileByEmail(("FloppyDisk"));
        assertNull(actual);
    }

    @Test
    void addNewProfile() {
        when(profileRepo.save(expected)).thenReturn(expected);
        assertEquals(expected, profileService.addNewProfile(expected));
    }

    @Test
<<<<<<< HEAD
    public void getProfileByUser()
    {
        Profile profile = new Profile(1,"user","1234","f","l","em", pList);
        when(profileRepo.getProfileByEmail(profile.getEmail())).thenReturn(profile);
        Profile actual = profileService.getProfileByEmail(profile.getEmail());
        assertEquals(profile,actual);
    }

    @Test
    public void testGetExistingProfile(){
        Profile expected = new Profile(1,"test","1234","test","test","test@mail", pList);
=======
    void getProfileByUser() {
        when(profileRepo.getProfileByEmail(expected.getEmail())).thenReturn(expected);
        Profile actual = profileService.getProfileByEmail(expected);
        assertEquals(expected,actual);
    }

    @Test
    void testGetExistingProfile() {
>>>>>>> main
        when(profileRepo.getProfileByPid(1)).thenReturn(expected);
        assertEquals(expected, profileService.getProfileByPid(1));
    }

    @Test
    void testGetInvalidProfile() {
        when(profileRepo.getProfileByPid(1)).thenReturn(null);
        assertNull(profileService.getProfileByPid(1));
    }

    @Test
<<<<<<< HEAD
    public void testUpdateExistingProfile(){
        Profile expected = new Profile(1,"test","1234","updateTest","updateTest","test@mail", pList);
        Profile old = new Profile(1,"test","1234","test","test","test@mail", pList);
        when(profileRepo.getProfileByPid(1)).thenReturn(old);
        when(profileRepo.save(old)).thenReturn(old);
        assertEquals(expected, profileService.updateProfile(expected));
    }

    @Test
    public void testUpdateInvalidProfile(){
        Profile profile = new Profile(1,"test","1234","updateTest","updateTest","test@mail", pList);
        when(profileRepo.getProfileByPid(1)).thenReturn(null);
=======
    void testUpdateExistingProfile() {
        when(profileRepo.getProfileByPid(expected.getPid())).thenReturn(expected);
        when(profileRepo.save(expected)).thenReturn(expected);
        assertEquals(this.expected, profileService.updateProfile(this.expected));
    }

    @Test
    void testUpdateInvalidProfile() {
        when(profileRepo.getProfileByPid(expected.getPid())).thenReturn(null);
>>>>>>> main
        when(profileRepo.save(null)).thenReturn(null);
        assertNull(profileService.updateProfile(expected));
    }
}
