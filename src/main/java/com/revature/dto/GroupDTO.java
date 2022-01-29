package com.revature.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.models.Group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GroupDTO {

	private @NonNull Integer groupId;
	
	private @NonNull String groupName;

	private Set<ProfileDTO> members;
	
	public GroupDTO(Group group) {
		groupId = group.getGroupId();
		groupName = group.getGroupName();
		members = new HashSet<>();
		group.getMembers().forEach(m -> {
			ProfileDTO profileDto = new ProfileDTO(m.getPid(), m.getUsername(), m.getPasskey(), m.getFirstName(), m.getLastName(), m.getEmail());
			List<ProfileDTO> following = new ArrayList<>();
			m.getFollowing().forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(), f.getPasskey(), f.getFirstName(), f.getLastName(), f.getEmail())));
			Set<GroupDTO> groups = new HashSet<>();
			m.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName())));
			profileDto.setFollowing(following);
			profileDto.setGroups(groups);
			members.add(profileDto);
		});
	}
	
}
