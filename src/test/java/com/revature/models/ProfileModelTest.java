package com.revature.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfileModelTest {

	private static final int PID = 0;
	private static final String USERNAME = "username";
	private static final String PASSKEY = "passkey";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String EMAIL = "email";
	private static final boolean VERIFICATION = false;
	private static final String IMG_URL = "imgurl";
	private static final String IMG_COVERURL="coverimgurl";
	private static Profile profile1;
	private static Profile profile2;
	private static List<Profile> following;
	private static Set<Group> groups;

	@BeforeEach
	void init() {
		following = new LinkedList<>();
		groups = new HashSet<>();
		profile1 = new Profile(PID, USERNAME, PASSKEY, FIRST_NAME, LAST_NAME, EMAIL, VERIFICATION, IMG_URL,IMG_COVERURL, following,
				groups);
		profile2 = new Profile(PID, USERNAME, PASSKEY, FIRST_NAME, LAST_NAME, EMAIL, VERIFICATION, IMG_URL,IMG_COVERURL, following,
				groups);
	}

	@Test
	void testGetters() {
		assertEquals(PID, profile1.getPid());
		assertEquals(USERNAME, profile1.getUsername());
		assertEquals(PASSKEY, profile1.getPasskey());
		assertEquals(FIRST_NAME, profile1.getFirstName());
		assertEquals(LAST_NAME, profile1.getLastName());
		assertEquals(EMAIL, profile1.getEmail());
		assertEquals(VERIFICATION, profile1.isVerification());
		assertEquals(IMG_URL, profile1.getImgurl());
		assertEquals(following, profile1.getFollowing());
		assertEquals(groups, profile1.getGroups());
	}

	@Test
	void testSetters() {
		Profile profile = new Profile();
		profile.setPid(PID);
		profile.setUsername(USERNAME);
		profile.setPasskey(PASSKEY);
		profile.setFirstName(FIRST_NAME);
		profile.setLastName(LAST_NAME);
		profile.setEmail(EMAIL);
		profile.setVerification(VERIFICATION);
		profile.setImgurl(IMG_URL);
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
