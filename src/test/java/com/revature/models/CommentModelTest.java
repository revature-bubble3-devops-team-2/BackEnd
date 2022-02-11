package com.revature.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentModelTest {

	private static final int CID = 0;
	private static final String C_BODY = "haha that's funny";
	private static Profile writer;
	private static Post post;
	private static Timestamp dateCreated;
	private static Comment previous;
	private static Comment comment1;
	private static Comment comment2;

	@BeforeEach
	void init() {
		writer = new Profile();
		post = new Post();
		dateCreated = new Timestamp(0);
		previous = new Comment();
		comment1 = new Comment(CID, writer, post, C_BODY, dateCreated, previous);
		comment2 = new Comment(CID, writer, post, C_BODY, dateCreated, previous);
	}

	@Test
	void testGetters() {
		assertEquals(CID, comment1.getCid());
		assertEquals(writer, comment1.getWriter());
		assertEquals(post, comment1.getPost());
		assertEquals(C_BODY, comment1.getCBody());
		assertEquals(dateCreated, comment1.getDateCreated());
		assertEquals(previous, comment1.getPrevious());
	}

	@Test
	void testSetters() {
		Comment comment = new Comment();
		comment.setCid(CID);
		comment.setWriter(writer);
		comment.setPost(post);
		comment.setCBody(C_BODY);
		comment.setDateCreated(dateCreated);
		comment.setPrevious(previous);
		assertEquals(comment1, comment);
	}

	@Test
	void testEquals() {
		assertEquals(comment1, comment2);
	}

	@Test
	void testHashCode() {
		assertEquals(comment1.hashCode(), comment2.hashCode());
	}

}
