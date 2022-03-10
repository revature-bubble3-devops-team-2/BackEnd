package com.revature.models;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "profile")
@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "groups", "following" })
@ToString(exclude = { "groups", "following" })
public class Profile {
	@Id
	@Column(name = "profile_id")
	private int pid;
	@Column(name = "username", columnDefinition = "TEXT", nullable = false, unique = true)
	private String username;
	@Column(name = "passkey", columnDefinition = "TEXT", nullable = false, unique = true)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String passkey;
	@Column(name = "first_name", columnDefinition = "TEXT", nullable = false)
	private String firstName;
	@Column(name = "last_name", columnDefinition = "TEXT", nullable = false)
	private String lastName;
	@Column(name = "email", columnDefinition = "TEXT", nullable = false, unique = true)
	private String email;
	@Column(name = "verification", columnDefinition = "BOOLEAN", nullable = false)
	private boolean verification = false;
	@Column(name = "imgurl", columnDefinition = "TEXT")
	private String imgurl;
	@Column(name = "coverimgurl", columnDefinition = "TEXT")
	private String coverimgurl;
	@Column(name = "following")
	@ManyToMany
	@JsonIgnore
	private List<Profile> following = new LinkedList<>();
	@ManyToMany(mappedBy = "members")
	private Set<Group> groups = new HashSet<>();

	public Profile() {
		super();
		this.pid = SecurityUtil.getId();
	}

	public Profile(int pid, String username, String passkey, String firstName, String lastName, String email, boolean verification) {
		this.pid = pid;
		this.username = username;
		this.passkey = passkey;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.verification = verification;
	}

	public Profile(String username, String passkey, String firstName, String lastName, String email, boolean verification) {
		this();
		this.username = username;
		this.passkey = passkey;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.verification = verification;
	}


	public Profile(String firstName) {
		this.firstName = firstName;
	}


}
