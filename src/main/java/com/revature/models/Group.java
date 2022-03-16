package com.revature.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.utilites.SecurityUtil;

import lombok.*;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "members" })
@ToString(exclude = { "members" })
public class Group {

	@Id
	private Integer groupId;

	@Column(name = "group_name", nullable = false, unique = true)
	private String groupName;

	@Column(name = "group_imgurl", columnDefinition = "TEXT")
	private String imgurl;

	@Column(name = "group_coverimgurl", columnDefinition = "TEXT")
	private String coverImgurl;

	@Column(name = "description", columnDefinition = "TEXT")
	private String Description;

	@ManyToOne
	private Profile owner;

	@ManyToMany
	@JoinTable(name = "profile_groups", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
	private Set<Profile> members = new HashSet<>();

	public Group() {
		super();
		groupId = SecurityUtil.getId();
	}
}
