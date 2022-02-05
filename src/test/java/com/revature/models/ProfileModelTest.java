package com.revature.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileModelTest {
	private static Profile profile1;
	private static Profile profile2;
	private static List<Profile> following;
	private static Set<Group> groups;
	
	@BeforeEach
	void init() {
		following = new LinkedList<>();
		groups = new HashSet<>();
		profile1 = new Profile(0, "test", "test", "test", "test", "test", true, "test", following, groups);
		profile2 = new Profile(0, "test", "test", "test", "test", "test", true, "test", following, groups);
	}

	@Test
	void testGetters() {
		assertEquals(0, profile1.getPid());
		assertEquals("test", profile1.getUsername());
		assertEquals("test", profile1.getPasskey());
		assertEquals("test", profile1.getFirstName());
		assertEquals("test", profile1.getLastName());
		assertEquals("test", profile1.getEmail());
		assertEquals(true, profile1.isVerification());
		assertEquals("test", profile1.getImgurl());
		assertEquals(following, profile1.getFollowing());
		assertEquals(groups, profile1.getGroups());
	}
	
	@Test
	void testSetters() {
		Profile profile = new Profile();
		profile.setPid(0);
		profile.setUsername("test");
		profile.setPasskey("test");
		profile.setFirstName("test");
		profile.setLastName("test");
		profile.setEmail("test");
		profile.setVerification(true);
		profile.setImgurl("test");
		profile.setFollowing(following);
		profile.setGroups(groups);
		assertEquals(profile1, profile);
	}
	
	@Test
	void testEquals() {
		assertEquals(profile1, profile2);
	}
	
	@Test
	void testHashCode() {
		assertEquals(profile1.hashCode(), profile2.hashCode());
	}
}
