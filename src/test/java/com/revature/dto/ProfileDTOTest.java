package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileDTODTOTest {
	private static ProfileDTO profileDto1;
	private static ProfileDTO profileDto2;
	private static List<ProfileDTO> following;
	private static Set<GroupDTO> groups;
	
	@BeforeEach
	void init() {
		following = new LinkedList<>();
		groups = new HashSet<>();
		profileDto1 = new ProfileDTO(0, "test", "test", "test", "test", "test", true, "test", following, groups);
		profileDto2 = new ProfileDTO(0, "test", "test", "test", "test", "test", true, "test", following, groups);
	}

	@Test
	void testGetters() {
		assertEquals(0, profileDto1.getPid());
		assertEquals("test", profileDto1.getUsername());
		assertEquals("test", profileDto1.getPasskey());
		assertEquals("test", profileDto1.getFirstName());
		assertEquals("test", profileDto1.getLastName());
		assertEquals("test", profileDto1.getEmail());
		assertEquals(true, profileDto1.isVerification());
		assertEquals("test", profileDto1.getImgurl());
		assertEquals(following, profileDto1.getFollowing());
		assertEquals(groups, profileDto1.getGroups());
	}
	
	@Test
	void testSetters() {
		ProfileDTO profileDto = new ProfileDTO();
		profileDto.setPid(0);
		profileDto.setUsername("test");
		profileDto.setPasskey("test");
		profileDto.setFirstName("test");
		profileDto.setLastName("test");
		profileDto.setEmail("test");
		profileDto.setVerification(true);
		profileDto.setImgurl("test");
		profileDto.setFollowing(following);
		profileDto.setGroups(groups);
		assertEquals(profileDto1, profileDto);
	}
	
	@Test
	void testEquals() {
		assertEquals(profileDto1, profileDto2);
	}
	
	@Test
	void testHashCode() {
		assertEquals(profileDto1.hashCode(), profileDto2.hashCode());
	}
}
