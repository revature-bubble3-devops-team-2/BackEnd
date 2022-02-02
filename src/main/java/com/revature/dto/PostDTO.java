package com.revature.dto;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.revature.models.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
@NoArgsConstructor
public class PostDTO {

	private Integer psid;

	private ProfileDTO creator;

	private String body;

	private String imgURL;

	private Timestamp datePosted;

	private Set<Integer> likes = new LinkedHashSet<>();

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

}