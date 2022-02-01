package com.revature.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.models.Group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "members" })
@ToString(exclude = { "members" })
public class GroupDTO {

	private Integer groupId;

	private String groupName;

	private ProfileDTO owner;

	private Set<ProfileDTO> members = new HashSet<>();

	public GroupDTO(Group group) {
		if (group != null) {
			groupId = group.getGroupId();
			groupName = group.getGroupName();
			owner = group.getOwner() != null ? new ProfileDTO(group.getOwner()) : null;
			if (group.getMembers() != null) {
				members = new HashSet<>();
				group.getMembers().forEach(m -> {
					ProfileDTO profileDto = new ProfileDTO(m.getPid(), m.getUsername(), m.getPasskey(),
							m.getFirstName(), m.getLastName(), m.getEmail(), m.getImgurl(), null, null);
					List<ProfileDTO> following = new ArrayList<>();
					if (m.getFollowing() != null) {
						m.getFollowing().forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(), f.getPasskey(),
										f.getFirstName(), f.getLastName(), f.getEmail(), f.getImgurl(), null, null)));
					}
					Set<GroupDTO> groups = new HashSet<>();
					if (m.getGroups() != null) {
						m.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName(), null, null)));
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

}
