package com.revature.dto;

import java.sql.Timestamp;

import com.revature.models.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
*
* The class is a data transfer object for the Comment model
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

	private Integer cid;

	private ProfileDTO writer;

	private PostDTO post;

	private String cBody;

	private Timestamp dateCreated;

	private CommentDTO previous;

	public CommentDTO(Comment comment) {
		if (comment != null) {
			cid = comment.getCid();
			writer = comment.getWriter() != null ? new ProfileDTO(comment.getWriter()) : null;
			post = comment.getPost() != null ? new PostDTO(comment.getPost()) : null;
			cBody = comment.getCBody();
			dateCreated = comment.getDateCreated();
			previous = comment.getPrevious() != null ? new CommentDTO(comment.getPrevious()) : null;
		}
	}

}
