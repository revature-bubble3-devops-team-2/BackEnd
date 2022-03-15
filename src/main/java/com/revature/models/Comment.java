package com.revature.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.revature.utilites.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
@AllArgsConstructor
public class Comment {

	@Id
	private int cid;

	@ManyToOne
	private Profile writer;

	@ManyToOne
	private Post post;

	@Column(name = "cbody")
	private String cBody;

	@Column(name = "date_created")
	private Timestamp dateCreated;

	@OneToOne
	private Comment previous;

	public Comment() {
		super();
		this.cid = SecurityUtil.getId();
	}

	public Comment(Profile writer, Post post, String cBody, Timestamp dateCreated, Comment previous) {
		this();
		this.writer = writer;
		this.post = post;
		this.cBody = cBody;
		this.dateCreated = dateCreated;
		this.previous = previous;
	}


}
