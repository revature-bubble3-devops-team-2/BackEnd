package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revature.dto.GroupDTO;
import com.revature.dto.ProfileDTO;
import com.revature.models.Group;
import com.revature.models.Profile;
import com.revature.services.GroupServiceImpl;
import com.revature.services.ProfileServiceImpl;


public class GroupControllerTests {
	public static Set<Profile> members = new HashSet();
	public static List<Group> list = new ArrayList();
	public static final Profile PROFILE = new Profile(1,"puser", "sadfjkeii", "tname", "tLname", "t@email.com", false );
	public static final Group GROUP = new Group(1, "gname", PROFILE, members);
	
	@Mock
	GroupServiceImpl gserv;
	
	@Mock
	ProfileServiceImpl pserv;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);

	}
	@Test
	public ResponseEntity<GroupDTO> testFindGroup() {
		
		when(gserv.findById(0)).thenReturn(GROUP);
		GroupDTO groupdto = new GroupDTO(gserv.findById(0));
		assertEquals(groupdto.getGroupId(), 1);
		return ResponseEntity.ok(groupdto);
	}
	
	@Test
	public void testFindAllGroups(){
		ArrayList<Group> gList = new ArrayList<Group>();
		gList.add(GROUP);
		when(gserv.findAllPaginated(1)).thenReturn(gList);
		List<Group> groups = gserv.findAllPaginated(1);
		List<GroupDTO> groupDTOs = new LinkedList<>();
		groups.forEach(g -> groupDTOs.add(new GroupDTO(g)));
		assertEquals(groupDTOs.get(0).getGroupId(), GROUP.getGroupId());
		
		
		
	}
	@Test
	public void testSaveGroup() {
		when(gserv.save(GROUP)).thenReturn(GROUP);
		GroupDTO groupDto = new GroupDTO(gserv.save(GROUP));
		assertEquals(groupDto.getGroupId(), GROUP.getGroupId());
		 
	}
	
	@Test
	public void testGetMembers() {
		Group group;
		GroupDTO gDTO = null;
		when(gserv.findById(1)).thenReturn(GROUP);
		if((group = gserv.findById(1)) != null) {
			 gDTO = new GroupDTO(group);
			}
		assertEquals(group.getGroupId(), gDTO.getGroupId());
		
	}
	@Test
	public void testUserJoin() {
		Group group = new Group();
		Set<Profile> members = GROUP.getMembers();
		when(gserv.findById(1)).thenReturn(GROUP);
		when(pserv.getProfileByPid(1)).thenReturn(PROFILE);
		Profile user = pserv.getProfileByPid(1);
		if(members.contains(user)) {
			
		}else {
			members.add(user);
			group.setMembers(members);
			
		}		
		assertEquals(group.getMembers(), GROUP.getMembers());
	}
	
	@Test
	public void testUserLeave() {
		Group group = new Group();
		Set<Profile> members = GROUP.getMembers();
		when(gserv.findById(1)).thenReturn(GROUP);
		when(pserv.getProfileByPid(1)).thenReturn(PROFILE);
		Profile user = pserv.getProfileByPid(1);
		if(!members.contains(user)) {
			
		}else {
			members.remove(user);
			group.setMembers(members);
			
		}
		assertFalse(group.getMembers().contains(user));
	}
	
	@Test
	public void testSearch() {
		List <Group> group = new ArrayList();
		list.add(GROUP);
		List<GroupDTO> groupDtos = new LinkedList<>();
		when(gserv.search("query")).thenReturn(list);
		group = gserv.search("query");
		group.forEach(p -> groupDtos.add(new GroupDTO(p)));
		
		assertEquals(group.get(0).getGroupId(), groupDtos.get(0).getGroupId());
	}
}
