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
 * @edit David Guijosa
 * @batch: 220118-UTA-JAVA-GCP-EM
 * 
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "members" })
@ToString(exclude = { "members" })
public class GroupDTO {

	private int groupId;

	private String groupName;

	private String imgurl;

	private String coverImgurl;

	private String description;

	private ProfileDTO owner;

	private Set<ProfileDTO> members = new HashSet<>();

	public GroupDTO() {
		super();
		groupId = SecurityUtil.getId();
	}

	/**
	 * Creates a GroupDTO by passing a Group
	 * Checks if the group is not empty and if its, it sets the non object variables
	 * then it check if there are any members and if there are it adds the followers
	 * and the groups they are following
	 * 
	 * @param group
	 * 
	 */

	public GroupDTO(Group group) {
		if (group != null) {
			groupId = group.getGroupId();
			groupName = group.getGroupName();
			owner = group.getOwner() != null ? new ProfileDTO(group.getOwner()) : null;
			coverImgurl = group.getCoverImgurl();
			imgurl = group.getImgurl();
			description = group.getDescription();
			if (group.getMembers() != null) {
				members = new HashSet<>();
				group.getMembers().forEach(m -> {
					ProfileDTO profileDto = new ProfileDTO(m.getPid(), m.getUsername(), m.getPasskey(),
							m.getFirstName(), m.getLastName(), m.getEmail(), m.isVerification(), m.getImgurl(),
							m.getImgurl(), null, null);
					List<ProfileDTO> following = new ArrayList<>();
					if (m.getFollowing() != null) {
						m.getFollowing()
								.forEach(f -> following.add(new ProfileDTO(f.getPid(), f.getUsername(), f.getPasskey(),
										f.getFirstName(), f.getLastName(), f.getEmail(), f.isVerification(),
										f.getImgurl(), f.getImgurl(), null, null)));
					}
					Set<GroupDTO> groups = new HashSet<>();
					if (m.getGroups() != null) {
						m.getGroups().forEach(g -> groups.add(new GroupDTO(g.getGroupId(), g.getGroupName(),
								g.getImgurl(), g.getDescription(), g.getCoverImgurl(), null, null)));
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
		if (members != null) {
			members.forEach(m -> newMembers.add(m.toProfile()));
		}
		return new Group(groupId, groupName, imgurl, coverImgurl, description, (owner != null ? owner.toProfile() : null),
				newMembers);
	}

	//groupDto1 = new GroupDTO(GROUP_ID, GROUP_NAME,GROUP_IMGURL,GROUP_COVER, owner, members);
	public GroupDTO(String groupName, String groupImgurl, String groupCover, String Description, ProfileDTO owner, Set<ProfileDTO> members) {
		this();
		this.groupName = groupName;
		this.imgurl = groupImgurl;
		this.description = Description;
		this.coverImgurl = groupCover;
		this.owner = owner;
		this.members = members;
	}

}
