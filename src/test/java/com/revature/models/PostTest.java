package com.revature.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostTest {

	private static final int PSID = 0;
	private static final String BODY = "body";
	private static final String IMG_URL = "imgurl";
	private static Post post1;
	private static Post post2;
	private static Profile creator;
	private static Timestamp timestamp;
	private static Set<Integer> likes;
	private static Group group;

	@BeforeEach
	void init() {
		creator = new Profile();
		group = new Group();
		timestamp = new Timestamp(0);
		likes = Set.of(1, 2, 3);
		post1 = new Post(PSID, creator, BODY, IMG_URL, timestamp, likes, group);
		post2 = new Post(PSID, creator, BODY, IMG_URL, timestamp, likes, group);
	}

	@Test
	void testGetters() {
		assertEquals(PSID, post1.getPsid());
		assertEquals(creator, post1.getCreator());
		assertEquals(BODY, post1.getBody());
		assertEquals(IMG_URL, post1.getImgURL());
		assertEquals(timestamp, post1.getDatePosted());
		assertEquals(likes, post1.getLikes());
		assertEquals(group, post1.getGroup());
	}

	@Test
	void testSetters() {
		Post post = new Post();
		post.setPsid(PSID);
		post.setCreator(creator);
		post.setBody(BODY);
		post.setImgURL(IMG_URL);
		post.setDatePosted(timestamp);
		post.setLikes(likes);
		post.setGroup(group);
		assertEquals(post1, post);
	}

	@Test
	void testEquals() {
		assertEquals(post1, post2);
	}

	@Test
	void testHashCode() {
		assertEquals(post1.hashCode(), post2.hashCode());
	}

}
