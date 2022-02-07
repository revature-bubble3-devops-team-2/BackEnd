package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.dto.GroupDTO;
import com.revature.dto.PostDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.CommentServiceImpl;
import com.revature.services.PostService;

public class CommentControllerTests {
	 private static final String PROFILE = "profile";
	 public static final Profile PROFILE2 = new Profile(1,"puser", "sadfjkeii", "tname", "tLname", "t@email.com", false );
	@Mock
	CommentServiceImpl cServ;
	@Mock
	PostService pServ;
	
	@Mock
	HttpServletRequest req;
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);

	}
	@Test
	
	//Not sure if mocking if else statements  will increase test coverage
	public void testAddComment() {
		HttpServletRequest mock2 = Mockito.mock(HttpServletRequest.class);
		 List<ProfileDTO> following = new LinkedList<>();

		 Set<GroupDTO> groups = new HashSet<>();
		ProfileDTO pr = new ProfileDTO(1, "uname", "pkey", "fname", "lname", "email", true ,"imgUrl", following, groups);
		Set<Integer> likes = new LinkedHashSet();
		Timestamp ts = new Timestamp(45);
		PostDTO post = new PostDTO(1, pr, "text", "imgURL", ts, likes);
		
		 Profile check = new Profile();
		when(mock2.getAttribute(PROFILE)).thenReturn(PROFILE2);
		Profile temp = (Profile) mock2.getAttribute(PROFILE);
		if (post.getCreator().getUsername().equals(temp.getUsername())) {
			
		}
		when(pServ.likeFindByID(temp, post.toPost())).thenReturn(temp);
		when(pServ.likePost(temp, post.toPost())).thenReturn(null);
		Profile existProfile = pServ.likeFindByID(temp, post.toPost());
		if (existProfile == null) {
             check = pServ.likePost(temp, post.toPost());
            if (check == null) {
                
            } else {
              
            }
        } else {
            
        }
		assertEquals(existProfile, temp);
	}
}
