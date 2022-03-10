package com.revature.dto;

import java.util.ArrayList;
import java.util.HashSet;
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
* The class is a data transfer object for the Group model
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "members" })
@ToString(exclude = { "members" })
public class GroupDTO {

	private int groupId;

	private String groupName;

	private String groupImgurl;

	private ProfileDTO owner;

	private Set<ProfileDTO> members = new HashSet<>();

	public GroupDTO() {
		super();
		groupId = SecurityUtil.getId();
	}
	
	public GroupDTO(Group group) {
		if (group != null) {
			groupId = group.getGroupId();
			groupName = group.getGroupName();
			owner = group.getOwner() != null ? new ProfileDTO(group.getOwner()) : null;
			if (group.getMembers() != null) {
				members = new HashSet<>();
				group.getMembers().forEach(m -> {
					ProfileDTO profileDto = new ProfileDTO(m.getPid(), m.getUsername(), m.getPasskey(),
							m.getFirstName(), m.getLastName(), m.getEmail(), m.isVerification(), m.getImgurl(),m.getCoverimgurl(), null, null);
					List<ProfileDTO> following = new ArrayList<>();
					if (m.getFollowing() != null) {
						m.getFollowing().forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(), f.getPasskey(),
										f.getFirstName(), f.getLastName(), f.getEmail(), f.isVerification(), f.getImgurl(),f.getCoverimgurl(), null, null)));
					}
					Set<GroupDTO> groups = new HashSet<>();
					if (m.getGroups() != null) {
						m.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName(),g.getGroupImgurl(), null, null)));
					}
					profileDto.setFollowing(following);
					profileDto.setGroups(groups);
					members.add(profileDto);
				});
			} else {
				members = null;
			}
		}
	}
	
	public Group toGroup() {
		Set<Profile> newMembers = new HashSet<>();
		if(members != null) {
			members.forEach(m -> newMembers.add(m.toProfile()));
		}
		return new Group(groupId, groupName,groupImgurl, (owner != null ? owner.toProfile() : null), newMembers);
	}

	public GroupDTO(String groupName,String groupImgurl, ProfileDTO owner, Set<ProfileDTO> members) {
		this();
		this.groupName = groupName;
		this.owner = owner;
		this.members = members;
	}
	
}
