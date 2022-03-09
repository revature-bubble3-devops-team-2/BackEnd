package com.revature.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;

import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.revature.models.Group;
import com.revature.models.Post;
import com.revature.models.Profile;

/*class PostDTOTest {

	private static final int PSID = 0;
	private static final String BODY = "body";
	private static final String IMG_URL = "imgurl";
	private static PostDTO postDto1;
	private static PostDTO postDto2;
	private static Post post;
	private static ProfileDTO creator;
	private static Profile modelCreator;
	private static Timestamp dateCreated;
	private static Set<Integer> likes;
	private static GroupDTO group;
	private static Group modelGroup;

	@BeforeEach
	void init() {
		creator = new ProfileDTO();
		modelCreator = creator.toProfile();
		group = new GroupDTO();
		modelGroup = group.toGroup();
		dateCreated = new Timestamp(0);
		likes = new HashSet<>();

		likes.addAll(Arrays.asList(1,2,3));

		postDto1 = new PostDTO(PSID, creator, BODY, IMG_URL, dateCreated, likes, group);
		postDto2 = new PostDTO(PSID, creator, BODY, IMG_URL, dateCreated, likes, group);
		//postDto3 = new PostDTO(creator, BODY, IMG_URL, dateCreated, bookmarks);
		post = new Post(PSID, modelCreator, BODY, IMG_URL, dateCreated, likes, modelGroup);
	}

	@Test
	void testGetters() {
		assertEquals(PSID, postDto1.getPsid());
		assertEquals(creator, postDto1.getCreator());
		assertEquals(BODY, postDto1.getBody());
		assertEquals(IMG_URL, postDto1.getImgURL());
		assertEquals(dateCreated, postDto1.getDatePosted());
		assertEquals(likes, postDto1.getLikes());
		assertEquals(group, postDto1.getGroup());
	}

	@Test
	void testSetters() {
		PostDTO postDto = new PostDTO();
		postDto.setPsid(PSID);
		postDto.setCreator(creator);
		postDto.setBody(BODY);
		postDto.setImgURL(IMG_URL);
		postDto.setDatePosted(dateCreated);
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

	@Test
	void testModelConstructor() {
		assertEquals(postDto1, new PostDTO(post));
	}

	@Test
	void testToPost() {
		assertEquals(post, postDto1.toPost());
	}

	@Test
	void testCustomConstructorNoPSID() {
		PostDTO pDto = new PostDTO(creator, BODY, IMG_URL, dateCreated, likes, group);
		assertNotEquals(postDto1.getPsid(), pDto.getPsid());
		pDto.setPsid(postDto1.getPsid());
		assertEquals(postDto1, pDto);
	}
	
	@Test
	void testCustomConstructorNoPSIDNoLikes() {
		PostDTO pDto = new PostDTO(creator, BODY, IMG_URL, dateCreated, group);
		assertNotEquals(postDto1.getPsid(), pDto.getPsid());
		pDto.setPsid(postDto1.getPsid());
		pDto.setLikes(postDto1.getLikes());
		assertEquals(postDto1, pDto);
	}
	
}*/
