package com.revature.dto;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.revature.models.Group;
import com.revature.models.Profile;
import com.revature.utilites.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(exclude = { "groups", "following" })
@ToString(exclude = { "groups", "following" })
public class ProfileDTO {

	private int pid;

	private String username;

	private String passkey;

	private String firstName;

	private String lastName;

	private String email;

	private boolean verification;

	private String imgurl;

	private String coverimgurl;

	private List<ProfileDTO> following = new LinkedList<>();

	private Set<GroupDTO> groups = new HashSet<>();

	public ProfileDTO() {
		super();
		pid = SecurityUtil.getId();
	}

	public ProfileDTO(Profile profile) {
		if (profile != null) {
			pid = profile.getPid();
			username = profile.getUsername();
			passkey = profile.getPasskey();
			firstName = profile.getFirstName();
			lastName = profile.getLastName();
			email = profile.getEmail();
			verification = profile.isVerification();
			imgurl = profile.getImgurl();
			coverimgurl = profile.getCoverImgurl();
			following = null;
			if (profile.getFollowing() != null) {
				following = new LinkedList<>();
				profile.getFollowing().forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(),
						f.getPasskey(), f.getFirstName(), f.getLastName(), f.getEmail(), f.isVerification(),
						f.getImgurl(), f.getCoverImgurl(), null, null)));
			}
			groups = null;
			if (profile.getGroups() != null) {
				groups = new HashSet<>();
				profile.getGroups().forEach(g -> groups.add(
						new GroupDTO(g.getGroupId(), g.getGroupName(), g.getImgurl(), g.getCoverImgurl(), g.getDescription(), null, null)));
			}
		}
	}

	public boolean isIncomplete() {
		return this.username == null || this.passkey == null || this.firstName == null || this.lastName == null
				|| this.email == null || this.following == null || this.groups == null || this.username.isEmpty()
				|| this.passkey.isEmpty() || this.firstName.isEmpty() || this.lastName.isEmpty() || this.email.isEmpty()
				|| this.pid < 100;
	}

	public Profile toProfile() {
		List<Profile> newFollowing = new LinkedList<>();
		if (following != null) {
			following.forEach(f -> newFollowing.add(f.toProfile()));
		}
		Set<Group> newGroups = new HashSet<>();
		if (groups != null) {
			groups.forEach(g -> newGroups.add(g.toGroup()));
		}
		return new Profile(pid, username, passkey, firstName, lastName, email, verification, imgurl, coverimgurl,
				newFollowing, newGroups);
	}

	public ProfileDTO(String username, String passkey, String firstName, String lastName, String email,
			boolean verification,
			String imgurl, String coverimgurl, List<ProfileDTO> following, Set<GroupDTO> groups) {
		this();
		this.username = username;
		this.passkey = passkey;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.verification = verification;
		this.imgurl = imgurl;
		this.coverimgurl = coverimgurl;
		this.following = following;
		this.groups = groups;
	}

}
