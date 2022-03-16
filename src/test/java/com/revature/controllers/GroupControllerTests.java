package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revature.dto.GroupDTO;
import com.revature.dto.PostDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Group;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.GroupServiceImpl;
import com.revature.services.PostServiceImpl;
import com.revature.services.ProfileServiceImpl;


class GroupControllerTests {
	private static Set<Profile> members = new HashSet<>();
	private static List<Group> list = new ArrayList<>();
	private static List<Post> posts = new ArrayList<>();
	private static final Profile PROFILE = new Profile(1,"tazer", "sadfjkeii", "tazer", "tLname", "tazer@email.com", true );
	private static final Profile PROFILE2 = new Profile(2,"mazer", "sadfjkeii", "mazer", "tLname", "mazer@email.com", true );
	private static Group group;
	private static Group group2;
	private static final Post POST = new Post(3, PROFILE, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()), null);
	
	private GroupDTO groupDto;
	private GroupDTO testGroupDto;
	
	
	@Mock
	GroupServiceImpl gserv;
	
	@Mock
	ProfileServiceImpl pserv;
	
	@Mock
	PostServiceImpl postServ;
	
	@InjectMocks
	GroupController groupController;
	
	@BeforeEach
	void initMock() {
		MockitoAnnotations.openMocks(this);
		members.add(PROFILE);
		members.add(PROFILE2);
		group = new Group(1, "dodgeball","","","", PROFILE, members);
		Set<Profile> singleMember = new HashSet<>();
		singleMember.add(PROFILE2);
		group2 = new Group(2, "hodgeball","","","", PROFILE2, singleMember);
		POST.setGroup(group);
		posts.add(POST);
		
		groupDto = new GroupDTO(group);
		testGroupDto = new GroupDTO(group2);
		
	}
	
	@Test
	void testSaveGroup() {
		when(gserv.save(group)).thenReturn(group);
		ResponseEntity<GroupDTO> responseEntity = groupController.saveGroup(groupDto);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
		 
	}
	
	@Test
	void testFindGroup() {
		when(gserv.findById(1)).thenReturn(group);
		ResponseEntity<GroupDTO> responseEntity = groupController.findGroup(1);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		
	}
	
	@Test
	void testFindAllGroups(){
		int pageRequested = 1;
		list.add(group);
		list.add(group2);
		when(gserv.findAllPaginated(any(Integer.class))).thenReturn(list);
		ResponseEntity<List<GroupDTO>> gDtoList = groupController.findAllGroups(pageRequested);
		List<Group> groups = gDtoList.getBody().stream().map(gDto -> gDto.toGroup()).collect(Collectors.toList());
		assertEquals(HttpStatus.OK.value(), gDtoList.getStatusCodeValue());
		for(int index =0; index<list.size();index++) {
			assertEquals(list.get(index).getGroupId(), groups.get(index).getGroupId());
		}
		
		
	}
	
	@Test
	void testGetMembers() {
		when(gserv.findById(1)).thenReturn(group);
		ResponseEntity<Set<ProfileDTO>> responseEntity = groupController.getMembers(1);
		Set<Profile> profiles = responseEntity.getBody().stream().map(pdto -> pdto.toProfile()).collect(Collectors.toSet());
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		for ( Profile p : profiles) {
			assertTrue(members.contains(p));
		}
	}
	
	@Test
	void testGetPosts() {
		when(postServ.getAllGroupPosts(any(Integer.class))).thenReturn(posts);
		ResponseEntity<List<PostDTO>> responseEntity = groupController.getPosts(1);
		List<Post> postList = responseEntity.getBody().stream().map(pdto -> pdto.toPost()).collect(Collectors.toList());
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		for ( Post p : postList) {
			assertTrue(posts.contains(p));
		}
	}
	
	@Test
	void testUserJoin() {
		when(gserv.findById(any(Integer.class))).thenReturn(group2);
		when(pserv.getProfileByPid(any(Integer.class))).thenReturn(PROFILE);
		when(gserv.save(any(Group.class))).thenReturn(group2);
		ResponseEntity<GroupDTO> responseEntity = groupController.userJoin(2, 1);
		assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
		Group returnedGroup = responseEntity.getBody().toGroup();
		assertTrue(returnedGroup.getMembers().contains(PROFILE));
	}
	
	@Test
	void testUserLeave() {
		when(gserv.findById(any(Integer.class))).thenReturn(group);
		when(pserv.getProfileByPid(any(Integer.class))).thenReturn(PROFILE2);
		when(gserv.save(any(Group.class))).thenReturn(group);
		ResponseEntity<GroupDTO> responseEntity = groupController.userLeave(1, 2);
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		Group returnedGroup = responseEntity.getBody().toGroup();
		assertFalse(returnedGroup.getMembers().contains(PROFILE2));
	}
	
	@Test
	void testSearch() {
		list.add(group);
		list.add(group2);
		String query = "ball";
		when(gserv.search(query)).thenReturn(list);
		ResponseEntity<List<GroupDTO>> responseEntity = groupController.search(query);
		List<Group> groups = responseEntity.getBody().stream().map(gDto -> gDto.toGroup()).collect(Collectors.toList());
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
		for(int index = 0; index < groups.size(); index++){
	    	  assertEquals(list.get(index).getGroupId(), groups.get(index).getGroupId());
	      }
	}
	
	@Test
	void testGetMembersInvalidGroupId() {
		when(gserv.findById(0)).thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, groupController.getMembers(0).getStatusCode());
	}
	
	@Test
	void testGetPostsInvalidGroupId() {
		when(postServ.getAllGroupPosts(0)).thenReturn(null);
		assertEquals(HttpStatus.NOT_FOUND, groupController.getPosts(0).getStatusCode());
	}
	
	@Test
	void testUserJoinInvalidGroupId() {
		when(gserv.findById(0)).thenReturn(null);
		assertEquals(HttpStatus.BAD_REQUEST, groupController.userJoin(0, 0).getStatusCode());
	}
	
	@Test
	void testUserLeaveInvalidGroupId() {
		when(gserv.findById(0)).thenReturn(null);
		assertEquals(HttpStatus.BAD_REQUEST, groupController.userLeave(0, 0).getStatusCode());
	}
	
}
