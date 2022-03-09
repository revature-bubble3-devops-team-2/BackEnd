package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.ProfileServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
	 private static final String USERNAME = "dummyUsername";
	 private static final String PASSWORD = "abc123";
	 private static final String EMAIL = "dummy@email.com";
	 private static final boolean VERIFICATION = true;
	 private static Profile expected = new Profile();

	 private static final String USERNAME2 = "dummyUsername2";
	 private static final String PASSWORD2 = "abc123";
	 private static final String EMAIL2 = "dummy2@email.com";
	 private static Profile expected2 = new Profile();
	 
	 private static final String TOKEN_NAME = "Authorization";
	
	 private ProfileDTO profiledto;
	 private ProfileDTO testprofiledto;
	 private MockHttpServletRequest request;
	 
	 @Mock
	 private ProfileServiceImpl profileService;

	 @InjectMocks
	 ProfileController profileController;

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

	       expected = new Profile(USERNAME, passkey, name, name, EMAIL, VERIFICATION);
	       String name2 = "dummyName2";
	       String passkey2 = "c8ZLBnfDh3YsvZ2dW1KDWY6ZTak8+v+/L74e8Vfoydk1IfySsVCAZVKTZfrtPKodzUXEiR+69yjOz1qqf7U4rA==jnW"
	                +
	                "2sIxW7inUlQqGJCNrNa7Eavj5uMGQAYZ0S6xNz65p79QaOk8eZpOChJlFPvIadohhOuHg5PFGeewM2YmkVR260YPhJwK/GUR3YXs" +
	                "UH0+KjOQKuHAHY8CyLwpBsNV0DsPO56jn2As1CfBMJc9VSNsx37W4Vo5MCaOTisZwBWjUsTG9i+HecqcK9C0tIWC2Jn44oX5BAS3" +
	                "1Huev/ZIWf2OE3FjHSMLyJs0TmJAPxBg6IllwEhv75+ffGbZENZVkkHV3LRxUWKtGdQa5tSOt6pdzIZtdSO6o81zXD6BBDfEZo22" +
	                "qCVCR2C2DQbsmaMJhInHwxEZ3RS3/1N+rz85STq/h+nKvqBcoPOFfq0o5tRRnOlRVFpWuONhiY7IUSqtTZCJmqHnALSRFJwMGFPj" +
	                "mUkq1nQxux1rgxllacVb+AT4bS+Xbw8DyUwGepmTCiw4t75krGyCSBArcmfiFBtgDkVZTFIJ+GMFhFbpWv2darLcxKlgSdur/z9Y" +
	                "CYoZcKm9vrrH+CaFykfIUdjnln5jhLoRmjeBIHgYWITG5J5/NCzAM+a3k4Y92/hbgDDE15GD1ud1EU8GHY4eb5LU1pAb2O7zbcW9" +
	                "pQbtVcbqyJGNRFA6OAGcWb1R0+04d0+1DA6BjTDsxkltgsvUpLrVFBo4VaFAT6Jf4ZI2Pg39WjFY1an8=";
	       expected2 = new Profile(USERNAME2, passkey2, name2, name2, EMAIL2, VERIFICATION);
	       profiledto = new ProfileDTO();
	       profiledto.setUsername(USERNAME);
	       profiledto.setPasskey(passkey);
	       profiledto.setFirstName(name);
	       profiledto.setLastName(name);
	       profiledto.setEmail(EMAIL);
	       profiledto.setVerification(VERIFICATION);
	       
	       testprofiledto = new ProfileDTO();
	       testprofiledto.setUsername(USERNAME2);
	       testprofiledto.setPasskey(passkey2);
	       testprofiledto.setFirstName(name2);
	       testprofiledto.setLastName(name2);
	       testprofiledto.setEmail(EMAIL2);
	       testprofiledto.setVerification(VERIFICATION);
	       
	       request = new MockHttpServletRequest();
	  }
	 
	 @BeforeEach
	 void initEachMock() {
		 MockitoAnnotations.openMocks(this);
	 }

	  @Test
	  void testRegister() throws Exception {
		  when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(expected);
		  ResponseEntity<ProfileDTO> responseEntity = profileController.addNewProfile(profiledto);
		  assertNotNull(responseEntity);
		  assertEquals(HttpStatus.IM_USED.value(), responseEntity.getStatusCodeValue());
	  }
	 
	  @Test
	  void testLogin() {
		  when(profileService.login(any(String.class),any(String.class) )).thenReturn(expected);
		  ResponseEntity<ProfileDTO> responseEntity = profileController.login(USERNAME, PASSWORD);
		  assertNotNull(responseEntity);
		  assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
	  }
	  
	  @Test
	  void testGetProfile() {
		  when(profileService.getProfileByPid(any(Integer.class))).thenReturn(expected);
		  ResponseEntity<ProfileDTO> responseEntity = profileController.getProfileByPid(1);
		  assertNotNull(responseEntity);
		  assertEquals(HttpStatus.ACCEPTED.value(), responseEntity.getStatusCodeValue());
	  }
	  
	  @Test
	  void testUpdateProfile() {
		  when(profileService.updateProfile(any(Profile.class))).thenReturn(expected2);
		  ResponseEntity<ProfileDTO> responseEntity = profileController.updateProfile(testprofiledto);
		  assertEquals(responseEntity.getBody().toProfile().getFirstName(), expected2.getFirstName());
		  assertEquals(HttpStatus.ACCEPTED.value(), responseEntity.getStatusCodeValue());
	  }
	  
	  @Test
	  void testUnfollow() {
		  HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		  when(req.getAttribute(any(String.class))).thenReturn(expected);
		  when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(expected);
		  when(profileService.removeFollowByEmail(any(Profile.class), any(String.class))).thenReturn(expected);
		  ResponseEntity<String> responseEntity = profileController.unfollow(expected.getEmail(), req);
		  assertEquals(HttpStatus.ACCEPTED.value(), responseEntity.getStatusCodeValue()); 
	  }
	  
	  @Test
	  void testGetAll() {
		  List<Profile> profileList = Arrays.asList(expected, expected2);
		  int pageRequested = 1;
		  
	      when(profileService.getAllProfilesPaginated(any(Integer.class))).thenReturn(profileList);
	      ResponseEntity<List<ProfileDTO>> list = profileController.getAllPostsbyPage(pageRequested);
	      List<Profile> profiles = list.getBody().stream().map(pdto -> pdto.toProfile() ).collect(Collectors.toList());
	      assertEquals(HttpStatus.OK.value(),list.getStatusCodeValue());
	      for(int index = 0; index < profiles.size();index++){
	    	  assertEquals(profiles.get(index).getPid(), profileList.get(index).getPid());
	      }
	  }
	  
	  @Test
	  void testSearch() {
		  List<Profile> profileList = Arrays.asList(expected, expected2);
		  String query = "dummy";
	      when(profileService.search(any(String.class))).thenReturn(profileList);
	      ResponseEntity<List<ProfileDTO>> searchList = profileController.search(query);
	      List<Profile> profiles = searchList.getBody().stream().map(pdto -> pdto.toProfile() ).collect(Collectors.toList());
	      assertEquals(HttpStatus.OK.value(),searchList.getStatusCodeValue());
	      for(int index = 0; index < profiles.size(); index++) {
	    	  assertEquals(profiles.get(index).getPid(), profileList.get(index).getPid());
	      }
	  }
	  
	  @Test
	  void testGetFollowing() { 
		 List<Profile> profileList = Arrays.asList(expected, expected2);
		 when(profileService.getFollowers(any(Integer.class))).thenReturn(profileList);
		 ResponseEntity<List<ProfileDTO>> followingList = profileController.getFollowers(1);
		 List<Profile> actualProfileList = followingList.getBody().stream().map(profileDTO -> profileDTO.toProfile() ).collect(Collectors.toList());
		 assertEquals(HttpStatus.OK.value(), followingList.getStatusCodeValue());
		 for(int index = 0; index < actualProfileList.size(); index++) {
			 assertEquals(profileList.get(index).getPid(), actualProfileList.get(index).getPid());
		 }
	  }
	  
	  @Test
	  void testNewFollower() {
		  MockHttpServletRequest request = new MockHttpServletRequest();
		  when(profileService.getProfileByPid(any(Integer.class))).thenReturn(expected);
		  when(profileService.addFollowerByEmail(expected, EMAIL2)).thenReturn(expected2);
		  assertEquals(HttpStatus.ACCEPTED, profileController.newFollower(EMAIL2, expected.getPid(), request).getStatusCode());
	  }
	  
	  @Test
	  void testInvalidEmailNewFollower() {
		  MockHttpServletRequest request = new MockHttpServletRequest();
		  when(profileService.getProfileByPid(any(Integer.class))).thenReturn(expected);
		  when(profileService.addFollowerByEmail(expected, EMAIL2)).thenReturn(null);
		  assertEquals(HttpStatus.BAD_REQUEST, profileController.newFollower(EMAIL2, expected.getPid(), request).getStatusCode());
	  }
	  
	  @Test
	  void testAddNewProfileNewEmail() {
		  when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(null);
		  when(profileService.addNewProfile(any(Profile.class))).thenReturn(expected);
		  assertEquals(HttpStatus.CREATED, profileController.addNewProfile(profiledto).getStatusCode());
	  }
	  
	  @Test
	  void testAddNewProfileIncomplete() {
		  when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(null);
		  when(profileService.addNewProfile(any(Profile.class))).thenReturn(null);
		  assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, profileController.addNewProfile(profiledto).getStatusCode());
	  }
	  
	  @Test
	  void testLoginNullProfile() {
		  when(profileService.login(any(String.class), any(String.class))).thenReturn(null);
		  assertEquals(HttpStatus.UNAUTHORIZED, profileController.login(USERNAME, PASSWORD).getStatusCode());
	  }
	  
	  @Test
	  void testProfileByPidNullProfile() {
		  when(profileService.getProfileByPid(any(Integer.class))).thenReturn(null);
		  assertEquals(HttpStatus.BAD_REQUEST, profileController.getProfileByPid(0).getStatusCode());
	  }
	  
	  @Test
	  void testUpdateProfileNullProfile() {
		  when(profileService.updateProfile(any(Profile.class))).thenReturn(null);
		  assertEquals(HttpStatus.BAD_REQUEST, profileController.updateProfile(profiledto).getStatusCode());
	  }
	  
	  @Test
	  void testUnfollowNullFollower() {
		  when(profileService.getProfileByEmail(null)).thenReturn(null);
		  assertEquals(HttpStatus.BAD_REQUEST, profileController.unfollow(EMAIL, request).getStatusCode());
	  }
	  
}
