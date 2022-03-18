package com.revature.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupModelTest {

	private static final int GROUP_ID = 0;
	private static final String GROUP_NAME = "Friends";
	private static final String GROUP_IMGURL = "";
	private static final String DESCRIPTION = "";
	private static final String GROUP_COVER = "";
	private static Profile owner;
	private static Set<Profile> members;
	private static Group group1;
	private static Group group2;

	@BeforeEach
	void init() {
		owner = new Profile();
		members = new HashSet<>();
		members.add(owner);
		members.add(new Profile(3, "mt", "sfd45", "michael", "thomas", "mt@email.com", true));
		members.add(new Profile(5, "jill", "sxnfd5", "jillian", "davis", "jdavis@email.com", true));
		group1 = new Group(GROUP_ID, GROUP_NAME,GROUP_IMGURL,GROUP_COVER,DESCRIPTION, owner, members);
		group2 = new Group(GROUP_ID, GROUP_NAME,GROUP_IMGURL,GROUP_COVER,DESCRIPTION, owner, members);
	}

	@Test
	void testGetters() {
		assertEquals(GROUP_ID, group1.getGroupId());
		assertEquals(GROUP_NAME, group1.getGroupName());
		assertEquals(owner, group1.getOwner());
		assertEquals(members, group1.getMembers());
	}

	@Test
	void testSetters() {
		Group group = new Group();
		group.setGroupId(GROUP_ID);
		group.setGroupName(GROUP_NAME);
		group.setCoverImgurl(GROUP_COVER);
		group.setImgurl(GROUP_IMGURL);
		group.setDescription(DESCRIPTION);
		group.setOwner(owner);
		group.setMembers(members);
		assertEquals(group, group1);
	}

	@Test
	void testEquals() {
		assertEquals(group1, group2);
	}

	@Test
	void testHashCode() {
		assertEquals(group1.hashCode(), group2.hashCode());
	}

}