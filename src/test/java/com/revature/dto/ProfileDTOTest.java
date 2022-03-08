package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.models.Group;
import com.revature.models.Profile;

class ProfileDTOTest {

	private static final int PID = 0;
	private static final String USERNAME = "username";
	private static final String PASSKEY = "passkey";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String EMAIL = "email";
	private static final boolean VERIFICATION = false;
	private static final String IMG_URL = "imgurl";
	private static final String IMG_COVERURL="coverimgurl";
	private static ProfileDTO profileDto1;
	private static ProfileDTO profileDto2;
	private static Profile profile;
	private static List<ProfileDTO> following;
	private static List<Profile> modelFollowing;
	private static Set<GroupDTO> groups;
	private static Set<Group> modelGroups;

	@BeforeEach
	void init() {
		following = new LinkedList<>();
		modelFollowing = new LinkedList<>();
		groups = new HashSet<>();
		modelGroups = new HashSet<>();
		profileDto1 = new ProfileDTO(PID, USERNAME, PASSKEY, FIRST_NAME, LAST_NAME, EMAIL, VERIFICATION, IMG_URL,IMG_COVERURL,
				following, groups);
		profileDto2 = new ProfileDTO(PID, USERNAME, PASSKEY, FIRST_NAME, LAST_NAME, EMAIL, VERIFICATION, IMG_URL,IMG_COVERURL,
				following, groups);
		profile = new Profile(PID, USERNAME, PASSKEY, FIRST_NAME, LAST_NAME, EMAIL, VERIFICATION, IMG_URL,IMG_COVERURL,
				modelFollowing, modelGroups);
	}

	@Test
	void testGetters() {
		assertEquals(PID, profileDto1.getPid());
		assertEquals(USERNAME, profileDto1.getUsername());
		assertEquals(PASSKEY, profileDto1.getPasskey());
		assertEquals(FIRST_NAME, profileDto1.getFirstName());
		assertEquals(LAST_NAME, profileDto1.getLastName());
		assertEquals(EMAIL, profileDto1.getEmail());
		assertEquals(VERIFICATION, profileDto1.isVerification());
		assertEquals(IMG_URL, profileDto1.getImgurl());
		assertEquals(following, profileDto1.getFollowing());
		assertEquals(groups, profileDto1.getGroups());
	}

	@Test
	void testSetters() {
		ProfileDTO profileDto = new ProfileDTO();
		profileDto.setPid(PID);
		profileDto.setUsername(USERNAME);
		profileDto.setPasskey(PASSKEY);
		profileDto.setFirstName(FIRST_NAME);
		profileDto.setLastName(LAST_NAME);
		profileDto.setEmail(EMAIL);
		profileDto.setVerification(VERIFICATION);
		profileDto.setImgurl(IMG_URL);
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

	@Test
	void testModelConstructor() {
		assertEquals(profileDto1, new ProfileDTO(profile));
	}

	@Test
	void testToProfile() {
		assertEquals(profile, profileDto1.toProfile());
	}

	@Test
	void testCustomConstructor() {
		ProfileDTO pDto = new ProfileDTO(USERNAME, PASSKEY, FIRST_NAME, LAST_NAME, EMAIL, VERIFICATION, IMG_URL,IMG_COVERURL,
				following, groups);
		assertNotEquals(profileDto1.getPid(), pDto.getPid());
		pDto.setPid(profileDto1.getPid());
		assertEquals(profileDto1, pDto);
	}
}
