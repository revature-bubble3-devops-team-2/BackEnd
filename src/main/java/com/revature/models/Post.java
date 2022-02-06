package com.revature.models;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.revature.utilites.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
public class Post {

	@Id
	@Column(name = "post_id", unique = true, nullable = false)
	private int psid;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile creator;

	@Column(name = "body")
	private String body;

	@Column(name = "image_url", columnDefinition = "TEXT")
	private String imgURL;

	@Column(name = "date_posted", nullable = false)
	private Timestamp datePosted;

	@CollectionTable(name = "likes", joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "post_id"))
	@Column(name = "profile_id")
	@ElementCollection()
	private Set<Integer> likes = new LinkedHashSet<>();
	
	@ManyToOne
	private Group group;

	public Post() {
		super();
		this.psid = SecurityUtil.getId();

	}

	public Post(Profile creator, String body, String imgURL, Timestamp datePosted, Group group) {
		this();
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.datePosted = datePosted;
		this.group = group;
	}

	public Post(Profile creator, String body, String imgURL, Timestamp datePosted, Set<Integer> likes, Group group) {
		this();
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.datePosted = datePosted;
		this.likes = likes;
		this.group = group;
	}

	public Post(int psid, Profile creator, String body, String imgURL, Timestamp dateposted, Group group) {
		this.psid = psid;
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.datePosted = dateposted;
		this.group = group;
	}

}