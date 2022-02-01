package com.revature.dto;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.revature.models.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
*
* The class is a data transfer object for the Profile model
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "groups", "following" })
@ToString(exclude = { "groups", "following" })
public class ProfileDTO {

	private @NonNull Integer pid;

	private @NonNull String username;

	private @NonNull String passkey;

	private @NonNull String firstName;

	private @NonNull String lastName;

	private @NonNull String email;

    private String imgurl;
	
	private List<ProfileDTO> following = new LinkedList<>();

	private Set<GroupDTO> groups = new HashSet<>();

	public ProfileDTO(Profile profile) {
		if (profile != null) {
			pid = profile.getPid();
			username = profile.getUsername();
			passkey = profile.getPasskey();
			firstName = profile.getFirstName();
			lastName = profile.getLastName();
			email = profile.getEmail();
			imgurl = profile.getImgurl();
			following = new LinkedList<>();
			if (profile.getFollowing() != null) {
				profile.getFollowing().forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(),
						f.getPasskey(), f.getFirstName(), f.getLastName(), f.getEmail(), f.getImgurl(), null, null)));
			}
			groups = new HashSet<>();
			if (profile.getGroups() != null) {
				profile.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName())));
			}
		}
	}

	public boolean isIncomplete() {
		return this.username == null || this.passkey == null || this.firstName == null || this.lastName == null
				|| this.email == null || this.following == null || this.groups == null || this.username.isEmpty()
				|| this.passkey.isEmpty() || this.firstName.isEmpty() || this.lastName.isEmpty() || this.email.isEmpty()
				|| this.pid < 100;
	}

}
