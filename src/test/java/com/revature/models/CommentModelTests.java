package com.revature.models;



import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommentModelTests {

	@Test
	public void TestGettersAndSetters() {
		Comment comment = new Comment();
		Comment prev = new Comment();
		String body = "haha that's funny";
		Timestamp timeStamp = new Timestamp(50);
		Post post = new Post();
		Profile test = new Profile();
		comment.setCBody(body);
		comment.setCid(56);
		comment.setDateCreated(timeStamp);
		comment.setPost(post);
		comment.setPrevious(prev);
		comment.setWriter(test);
		comment.getCBody();
		comment.getCid();
		comment.getDateCreated();
		comment.getPost();
		comment.getPrevious();
		comment.getWriter();
		
		Assertions.assertEquals(comment.getCBody(), "haha that's funny");
		Assertions.assertEquals(comment.getCid(), 56);
		Assertions.assertEquals(comment.getDateCreated(), timeStamp);
		Assertions.assertEquals(comment.getPost(), post);
		Assertions.assertEquals(comment.getPrevious(), prev);
		Assertions.assertEquals(comment.getWriter(), test);
		
	}
}
