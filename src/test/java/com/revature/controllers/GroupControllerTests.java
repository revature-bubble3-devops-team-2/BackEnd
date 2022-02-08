package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
import com.revature.dto.ProfileDTO;
import com.revature.models.Group;
import com.revature.models.Profile;
import com.revature.services.GroupServiceImpl;
import com.revature.services.ProfileServiceImpl;


class GroupControllerTests {
	private static Set<Profile> members = new HashSet<>();
	private static List<Group> list = new ArrayList<>();
	private static final Profile PROFILE = new Profile(1,"tazer", "sadfjkeii", "tazer", "tLname", "tazer@email.com", true );
	private static final Profile PROFILE2 = new Profile(2,"mazer", "sadfjkeii", "mazer", "tLname", "mazer@email.com", true );
	private static Group group;
	private static Group group2;
	
	private GroupDTO groupDto;
	private GroupDTO testGroupDto;
	
	
	@Mock
	GroupServiceImpl gserv;
	
	@Mock
	ProfileServiceImpl pserv;
	
	@InjectMocks
	GroupController groupController;
	
	@BeforeEach
	void initMock() {
		MockitoAnnotations.openMocks(this);
		members.add(PROFILE);
		members.add(PROFILE2);
		group = new Group(1, "dodgeball", PROFILE, members);
		Set<Profile> singleMember = new HashSet<>();
		singleMember.add(PROFILE2);
		group2 = new Group(2, "hodgeball", PROFILE2, singleMember);
		
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
		for(int index = 0; index<2;index++){
	    	  assertEquals(list.get(index).getGroupId(), groups.get(index).getGroupId());
	      }
	}
}
