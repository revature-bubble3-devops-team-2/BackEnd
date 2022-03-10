package com.revature.dto;

import java.sql.Timestamp;

import com.revature.models.Comment;
import com.revature.utilites.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

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
public class CommentDTO {

	private int cid;

	private ProfileDTO writer;

	private PostDTO post;

	private String cBody;

	private Timestamp dateCreated;

	private CommentDTO previous;

	public CommentDTO() {
		super();
		cid = SecurityUtil.getId();
	}
	
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
	
	public Comment toComment() {
		return new Comment(cid, (writer != null ? writer.toProfile() : null), (post != null ? post.toPost() : null),
				cBody, dateCreated, (previous != null ? previous.toComment() : null));
	}

	public CommentDTO(ProfileDTO writer, PostDTO post, String cBody, Timestamp dateCreated,
			CommentDTO previous) {
		this();
		this.writer = writer;
		this.post = post;
		this.cBody = cBody;
		this.dateCreated = dateCreated;
		this.previous = previous;
	}
	

}
