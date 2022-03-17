package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.models.Group;
import com.revature.models.Profile;

class GroupDTOTest {

	private static final int GROUP_ID = 0;
	private static final String GROUP_NAME = "Friends";
	private static final String GROUP_IMGURL = "";
	private static final String GROUP_COVER="";
	private static final String DESCRIPTION = "";
	private static ProfileDTO owner;
	private static Profile modelOwner;
	private static Set<ProfileDTO> members;
	private static Set<Profile> modelMembers;
	private static GroupDTO groupDto1;
	private static GroupDTO groupDto2;
	private static Group group;

	@BeforeEach
	void init() {
		owner = new ProfileDTO();
		modelOwner = owner.toProfile();
		members = new HashSet<>();
		members.add(owner);
		modelMembers = members.stream().map(m -> m.toProfile()).collect(Collectors.toSet());
		groupDto1 = new GroupDTO(GROUP_ID, GROUP_NAME,GROUP_IMGURL,DESCRIPTION,GROUP_COVER, owner, members);
		groupDto2 = new GroupDTO(GROUP_ID, GROUP_NAME,GROUP_IMGURL,DESCRIPTION,GROUP_COVER, owner, members);
		group = new Group(GROUP_ID, GROUP_NAME,GROUP_IMGURL,DESCRIPTION,GROUP_COVER, modelOwner, modelMembers);
	}

	@Test
	void testGetters() {
		assertEquals(GROUP_ID, groupDto1.getGroupId());
		assertEquals(GROUP_NAME, groupDto1.getGroupName());
		assertEquals(owner, groupDto1.getOwner());
		assertEquals(members, groupDto1.getMembers());
	}

	@Test
	void testSetters() {
		GroupDTO groupDto = new GroupDTO();
		groupDto.setGroupId(GROUP_ID);
		groupDto.setGroupName(GROUP_NAME);
		groupDto.setImgurl(GROUP_IMGURL);
		groupDto.setCoverImgurl(GROUP_COVER);
		groupDto.setDescription(DESCRIPTION);
		groupDto.setOwner(owner);
		groupDto.setMembers(members);
		assertEquals(groupDto, groupDto1);
	}

	@Test
	void testEquals() {
		assertEquals(groupDto1, groupDto2);
	}

	@Test
	void testHashCode() {
		assertEquals(groupDto1.hashCode(), groupDto2.hashCode());
	}

	@Test
	void testModelConstructor() {
		assertEquals(groupDto1, new GroupDTO(group));
	}

	@Test
	void testToGroup() {
		assertEquals(group, groupDto1.toGroup());
	}
	
	@Test
	void testCustomConstructor() {
		GroupDTO gDto = new GroupDTO(GROUP_NAME,GROUP_IMGURL,DESCRIPTION,GROUP_COVER, owner, members);
		assertNotEquals(groupDto1.getGroupId(), gDto.getGroupId());
		gDto.setGroupId(groupDto1.getGroupId());
		assertEquals(groupDto1, gDto);
	}

}