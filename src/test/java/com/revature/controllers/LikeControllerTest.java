package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import com.revature.dto.PostDTO;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostService;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest {

	@Mock
	PostService postService;
	
	@InjectMocks
	LikeController likeController;
	
	private static MockHttpServletRequest request;
	private static Post post;
	private static Profile creator;
	private static Profile profile;
	
	@BeforeEach
	void init() {
		creator = new Profile();
		creator.setUsername("username");
		
		profile = new Profile();
		
		request = new MockHttpServletRequest();
		request.setAttribute("profile", profile);
		
		post = new Post();
		post.setCreator(creator);
	}
	
	@Test
	void testAddLikeNullPost() {
		assertEquals(HttpStatus.BAD_REQUEST, likeController.addLike(null, request).getStatusCode());
	}
	
	@Test
	void testAddLikePostAlreadyLiked() {
		when(postService.likeFindByID(any(Profile.class), any(Post.class))).thenReturn(profile);
		assertEquals(HttpStatus.FOUND, likeController.addLike(new PostDTO(post), request).getStatusCode());
	}
	
	@Test
	void testAddLikeProfileError() {
		when(postService.likeFindByID(any(Profile.class), any(Post.class))).thenReturn(null);
		when(postService.likePost(any(Profile.class), any(Post.class))).thenReturn(null);
		assertEquals(HttpStatus.BAD_REQUEST, likeController.addLike(new PostDTO(post), request).getStatusCode());
	}
	
	@Test
	void testAddLikeSuccess() {
		when(postService.likeFindByID(any(Profile.class), any(Post.class))).thenReturn(null);
		when(postService.likePost(any(Profile.class), any(Post.class))).thenReturn(profile);
		assertEquals(HttpStatus.CREATED, likeController.addLike(new PostDTO(post), request).getStatusCode());
	}
	
	@Test
	void testRemoveLikeFailure() {
		when(postService.likeDelete(any(Profile.class), any(Post.class))).thenReturn(-1);
		assertEquals(HttpStatus.BAD_REQUEST, likeController.removeLike(new PostDTO(post), request).getStatusCode());
	}
	
	@Test
	void testRemoveLikeSuccess() {
		when(postService.likeDelete(any(Profile.class), any(Post.class))).thenReturn(0);
		assertEquals(HttpStatus.OK, likeController.removeLike(new PostDTO(post), request).getStatusCode());
	}
	
	@Test
	void testGetLikeFindFalse() {
		int numberOfLikes = 5;
		when(postService.likeGet(any(Post.class))).thenReturn(numberOfLikes);
		assertEquals(HttpStatus.OK, likeController.getLike(new PostDTO(post), false, request).getStatusCode());
		assertEquals(numberOfLikes, likeController.getLike(new PostDTO(post), false, request).getBody());
	}
	
	@Test
	void testGetLikeFindTrueBadRequest() {
		when(postService.likeFindByID(any(Profile.class), any(Post.class))).thenReturn(null);
		assertEquals(HttpStatus.OK, likeController.getLike(new PostDTO(post), true, request).getStatusCode());
		assertEquals(0, likeController.getLike(new PostDTO(post), true, request).getBody());
	}
	
	@Test
	void testGetLikeFindTrueProfileNotExists() {
		when(postService.likeFindByID(any(Profile.class), any(Post.class))).thenReturn(profile);
		assertEquals(HttpStatus.OK, likeController.getLike(new PostDTO(post), true, request).getStatusCode());
		assertEquals(1, likeController.getLike(new PostDTO(post), true, request).getBody());
	}
	
}
