package com.revature.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="groups")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Group {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private @NonNull Integer groupId;
	
	private @NonNull String groupName;
	
	@ManyToMany
	@JoinTable(
		  name = "profile_groups", 
		  joinColumns = @JoinColumn(name = "group_id"), 
		  inverseJoinColumns = @JoinColumn(name = "profile_id"))
	@JsonIgnore
	private Set<Profile> members;
	
}
