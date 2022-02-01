package com.revature.dto;

import java.sql.Timestamp;

import com.revature.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class CommentDTO {

	private @NonNull Integer cid;

	private @NonNull ProfileDTO writer;

	private @NonNull PostDTO post;

	private @NonNull String cBody;

	private @NonNull Timestamp dateCreated;

	private CommentDTO previous;

	public CommentDTO(Comment comment) {
		if (comment != null) {
			cid = comment.getCid();
			writer = new ProfileDTO(comment.getWriter());
			post = new PostDTO(comment.getPost());
			cBody = comment.getCBody();
			dateCreated = comment.getDateCreated();
			previous = new CommentDTO(comment.getPrevious());
		}
	}

}
