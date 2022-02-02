package com.revature.dto;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.revature.models.Post;
import com.revature.utilites.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
*
* The class is a data transfer object for the Post model
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Data
@AllArgsConstructor
public class PostDTO {

	private int psid;

	private ProfileDTO creator;

	private String body;

	private String imgURL;

	private Timestamp datePosted;

	private Set<Integer> likes = new LinkedHashSet<>();

	public PostDTO() {
		super();
		psid = SecurityUtil.getId();
	}
	
	public PostDTO(Post post) {
		if (post != null) {
			psid = post.getPsid();
			creator = post.getCreator() != null ? new ProfileDTO(post.getCreator()) : null;
			body = post.getBody();
			imgURL = post.getImgURL();
			datePosted = post.getDatePosted();
			likes = post.getLikes();
		}
	}
	
	public Post toPost() {
		if(creator == null) { 
			return null;
		}
		return new Post(psid, creator.toProfile(), body, imgURL, datePosted, likes);
	}
	
	public PostDTO(ProfileDTO creator, String body, String imgURL, Timestamp datePosted) {
		this();
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.datePosted = datePosted;
	}

	public PostDTO(ProfileDTO creator, String body, String imgURL, Timestamp datePosted, Set<Integer> likes) {
		this();
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.datePosted = datePosted;
		this.likes = likes;
	}
	
}