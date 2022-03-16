package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.revature.models.Group;
import com.revature.models.Profile;
import com.revature.repositories.GroupRepo;

class GroupServiceTest {
	
	private Profile tempProfile = new Profile();
	private Group tempGroup1 = new Group();
	private Group tempGroup2 = new Group();
	private List<Group> groups = new ArrayList<>();
	private Pageable pageable = PageRequest.of(0, 3, Sort.by("groupName").descending());
	
	
	
	@Mock
	GroupRepo groupRepo;
	
	@InjectMocks
	private GroupServiceImpl groupServ;
	
    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        groups = new ArrayList<>();
        tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2@mail.com", true);
        tempGroup1 = new Group(3234, "Group1","","","", tempProfile, new HashSet<>());
        tempGroup2 = new Group(3235, "Group2","","","", tempProfile, new HashSet<>());
    }
    
    @Test
    void testAddGroup() {
    	when(groupRepo.save(tempGroup2)).thenReturn(tempGroup2);
    	assertEquals(tempGroup2, groupServ.save(tempGroup2));
    }
    
    @Test
    void testAddInvalidGroup() {
    	tempGroup2 = new Group(0, null,null, null,null,null, new HashSet<>());
    	when(groupRepo.save(tempGroup2)).thenReturn(null);
    	assertNull(groupServ.save(tempGroup2));
    }
	
    @Test
    void testFindById() {
    	when(groupRepo.findById(3234)).thenReturn(Optional.of(tempGroup1));
    	assertEquals(tempGroup1, groupServ.findById(3234));
    }
    
    @Test
    void testFindNoneById() {
    	when(groupRepo.findById(234728)).thenReturn(Optional.empty());
    	assertNull(groupServ.findById(234728));
    }
    
    @Test
    void testFindAllPaginated() {
    	groups.add(tempGroup1);
    	groups.add(tempGroup2);    	
    	when(groupRepo.findAll(pageable)).thenReturn(new PageImpl<Group>(groups));
    	assertEquals(groups, groupServ.findAllPaginated(1));
    }
    
    @Test
    void testFindNonePaginated() {
    	when(groupRepo.findAll(pageable)).thenReturn(new PageImpl<Group>(groups));
    	assertEquals(new ArrayList<>(), groupServ.findAllPaginated(1));
    }
    
    @Test
    void testFindMembers() {
    	tempGroup1.getMembers().add(tempProfile);
    	when(groupRepo.findById(3234)).thenReturn(Optional.of(tempGroup1));
    	assertEquals(tempGroup1.getMembers(),groupServ.showGroups(3234));
    }
    
    @Test
    void testFindMembersNonExistentGroup() {
    	tempGroup1.getMembers().add(tempProfile);
    	when(groupRepo.findById(323783)).thenReturn(Optional.empty());
    	assertEquals(new HashSet<>(),groupServ.showGroups(323783));
    }
    
    @Test
    void testGroupSearch() {
    	groups.add(tempGroup1);
    	groups.add(tempGroup2);
    	
    	Group sampleGroup = new Group();
		sampleGroup.setGroupId(0);
		sampleGroup.setGroupName("group");
		
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
				 .withMatcher("groupName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				 .withIgnorePaths("groupId")
				 .withIgnorePaths("owner")
				 .withIgnorePaths("members");
				 
		 Example <Group> example = Example.of(sampleGroup, ignoringExampleMatcher);
		 
		 when(groupRepo.findAll(example)).thenReturn(groups);
		 
		 assertEquals(groups, groupServ.search("group"));
    }
    
    @Test
    void testGroupSearchNoResults() {

    	Group sampleGroup = new Group();
    	sampleGroup.setGroupId(0);
    	sampleGroup.setGroupName("grop");
    	
		ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
				 .withMatcher("groupName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				 .withIgnorePaths("groupId")
				 .withIgnorePaths("owner")
				 .withIgnorePaths("members");
    	
    	Example <Group> example = Example.of(sampleGroup, ignoringExampleMatcher);
    	
    	when(groupRepo.findAll(example)).thenReturn(groups);
    	
    	assertEquals(new ArrayList<>(), groupServ.search("grop"));
    }
    
}
