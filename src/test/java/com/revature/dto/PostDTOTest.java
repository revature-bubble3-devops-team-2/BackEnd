package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PostDTOTest {
	
	private static PostDTO postDto1;
	private static PostDTO postDto2;
	private static ProfileDTO creator;
	private static Timestamp timestamp;
	private static Set<Integer> likes;
	private static GroupDTO group;
	
	@BeforeEach
	void init() {
		creator = new ProfileDTO();
		group = new GroupDTO();
		timestamp = new Timestamp(0);
		likes = Set.of(1, 2, 3);
		postDto1 = new PostDTO(0, creator, "test", "test", timestamp, likes, group);
		postDto2 = new PostDTO(0, creator, "test", "test", timestamp, likes, group);
	}

	@Test
	void testGetters() {
		assertEquals(0, postDto1.getPsid());
		assertEquals(creator, postDto1.getCreator());
		assertEquals("test", postDto1.getBody());
		assertEquals("test", postDto1.getImgURL());
		assertEquals(timestamp, postDto1.getDatePosted());
		assertEquals(likes, postDto1.getLikes());
		assertEquals(group, postDto1.getGroup());
	}
	
	@Test
	void testSetters() {
		PostDTO postDto = new PostDTO();
		postDto.setPsid(0);
		postDto.setCreator(creator);
		postDto.setBody("test");
		postDto.setImgURL("test");
		postDto.setDatePosted(timestamp);
		postDto.setLikes(likes);
		postDto.setGroup(group);
		assertEquals(postDto1, postDto);
	}
	
	@Test
	void testEquals() {
		assertEquals(postDto1, postDto2);
	}
	
	@Test
	void testHashCode() {
		assertEquals(postDto1.hashCode(), postDto2.hashCode());
	}
	
}
