package com.revature.models;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupModelTest {

	@Test
	public void TestGettersAndSetters() {
		Group group = new Group();
		Profile profile1 = new Profile(3, "mt", "sfd45", "michael", "thomas", "mt@email.com", true );
		Profile profile2 = new Profile(3, "hbob", "sfd45", "henry", "bob", "hbob@email.com", true );
		Profile profile3 = new Profile(5, "jill", "sxnfd5", "jillian", "davis", "jdavis@email.com", true );
		HashSet<Profile> members = new HashSet();
		members.add(profile2);
		members.add(profile3);
		group.setGroupId(1);
		group.setGroupName("Friends");
		group.setMembers(members);
		group.setOwner(profile1);
		group.getGroupId();
		group.getGroupName();
		group.getMembers();
		group.getOwner();
		
		Assertions.assertEquals(group.getGroupId(), 1);
		Assertions.assertEquals(group.getGroupName(), "Friends");
		Assertions.assertEquals(group.getMembers(), members);
		Assertions.assertEquals(group.getOwner().getFirstName(), "michael");
	}
}

/*

public Profile(int pid, String username, String passkey, String firstName, String lastName, String email, boolean verification) {
		this.pid = pid;
		this.username = username;
		this.passkey = passkey;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.verification = verification;
	}
*/