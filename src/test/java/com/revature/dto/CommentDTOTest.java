package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;

class CommentDTOTest {

	private static final int CID = 0;
	private static final String C_BODY = "haha that's funny";
	private static ProfileDTO writer;
	private static Profile modelWriter;
	private static PostDTO post;
	private static Post modelPost;
	private static Timestamp dateCreated;
	private static CommentDTO previous;
	private static Comment modelPrevious;
	private static CommentDTO commentDto1;
	private static CommentDTO commentDto2;
	private static Comment comment;

	@BeforeEach
	void init() {
		writer = new ProfileDTO();
		modelWriter = writer.toProfile();
		post = new PostDTO();
		modelPost = post.toPost();
		dateCreated = new Timestamp(0);
		previous = new CommentDTO();
		modelPrevious = previous.toComment();
		commentDto1 = new CommentDTO(CID, writer, post, C_BODY, dateCreated, previous);
		commentDto2 = new CommentDTO(CID, writer, post, C_BODY, dateCreated, previous);
		comment = new Comment(CID, modelWriter, modelPost, C_BODY, dateCreated, modelPrevious);
	}

	@Test
	void testGetters() {
		assertEquals(CID, commentDto1.getCid());
		assertEquals(writer, commentDto1.getWriter());
		assertEquals(post, commentDto1.getPost());
		assertEquals(C_BODY, commentDto1.getCBody());
		assertEquals(dateCreated, commentDto1.getDateCreated());
		assertEquals(previous, commentDto1.getPrevious());
	}

	@Test
	void testSetters() {
		CommentDTO commentDto = new CommentDTO();
		commentDto.setCid(CID);
		commentDto.setWriter(writer);
		commentDto.setPost(post);
		commentDto.setCBody(C_BODY);
		commentDto.setDateCreated(dateCreated);
		commentDto.setPrevious(previous);
		assertEquals(commentDto1, commentDto);
	}

	@Test
	void testEquals() {
		assertEquals(commentDto1, commentDto2);
	}

	@Test
	void testHashCode() {
		assertEquals(commentDto1.hashCode(), commentDto2.hashCode());
	}

	@Test
	void testModelConstructor() {
		assertEquals(commentDto1, new CommentDTO(comment));
	}

	@Test
	void testToComment() {
		assertEquals(comment, commentDto1.toComment());
	}
	
	@Test
	void testCustomConstructor() {
		CommentDTO cDto = new CommentDTO(writer, post, C_BODY, dateCreated, previous);
		assertNotEquals(commentDto1.getCid(), cDto.getCid());
		cDto.setCid(commentDto1.getCid());
		assertEquals(commentDto1, cDto);
	}

}
