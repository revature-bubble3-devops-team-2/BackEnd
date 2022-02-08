package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.revature.dto.PostDTO;
import com.revature.models.Post;
import com.revature.services.PostServiceImpl;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

	@InjectMocks
    PostController postController;
	
	@Mock
    PostServiceImpl postServiceImpl;
	
	@Test
    void testInvalidAddPost() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(postServiceImpl.addPost(any(Post.class))).thenReturn(null);    
        PostDTO postDto = new PostDTO();
        ResponseEntity<PostDTO> responseEntity = postController.addPost(postDto, request);         
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }
	
	@Test
    void testValidAddPost() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(postServiceImpl.addPost(any(Post.class))).thenReturn(new Post());         
        PostDTO postDto = new PostDTO();
        ResponseEntity<PostDTO> responseEntity = postController.addPost(postDto, request);
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());
    }
	
	@Test
    void testGetAllPostsbyPage() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(postServiceImpl.getAllPostsPaginated(any(Integer.class))).thenReturn(new LinkedList<>());
        ResponseEntity<List<PostDTO>> responseEntity = postController.getAllPostsbyPage(0);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());    
    }
	
	@Test
    void testGetAllPostsbyPageNoArgs() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(postServiceImpl.getAllPosts()).thenReturn(new LinkedList<>());
        ResponseEntity<List<PostDTO>> responseEntity = postController.getAllPostsbyPage();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());    
    }
}
