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

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class PostDTO {

	private @NonNull Integer psid;

	private @NonNull ProfileDTO creator;

	private @NonNull String body;

	private @NonNull String imgURL;

	private @NonNull Timestamp datePosted;

	private Set<Integer> likes = new LinkedHashSet<>();

	public PostDTO(Post post) {
		if (post != null) {
			psid = post.getPsid();
			creator = new ProfileDTO(post.getCreator());
			body = post.getBody();
			imgURL = post.getImgURL();
			datePosted = post.getDatePosted();
			likes = post.getLikes();
		}
	}

}