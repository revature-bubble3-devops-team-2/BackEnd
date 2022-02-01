package com.revature.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.models.Group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "members" })
@ToString(exclude = { "members" })
public class GroupDTO {

	private @NonNull Integer groupId;

	private @NonNull String groupName;
	
	private ProfileDTO owner;

	private Set<ProfileDTO> members;

	public GroupDTO(Group group) {
		if (group != null) {
			groupId = group.getGroupId();
			groupName = group.getGroupName();
			owner = new ProfileDTO(group.getOwner());
			members = new HashSet<>();
			group.getMembers().forEach(m -> {
				ProfileDTO profileDto = new ProfileDTO(m.getPid(), m.getUsername(), m.getPasskey(), m.getFirstName(),
						m.getLastName(), m.getEmail());
				List<ProfileDTO> following = new ArrayList<>();
				if (m.getFollowing() != null) {
					m.getFollowing().forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(),
							f.getPasskey(), f.getFirstName(), f.getLastName(), f.getEmail())));
				}
				Set<GroupDTO> groups = new HashSet<>();
				if (m.getGroups() != null) {
					m.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName())));
				}
				profileDto.setFollowing(following);
				profileDto.setGroups(groups);
				members.add(profileDto);
			});
		}
	}

}
