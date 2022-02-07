package com.revature.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
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
@RunWith(JUnitPlatform.class)
public class PostControllerTest {

	@InjectMocks
    PostController postController;
	
	@Mock
    PostServiceImpl postServiceImpl;
	
	@Test
    public void testInvalidAddEmployee() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(postServiceImpl.addPost(any(Post.class))).thenReturn(null);
        
        PostDTO postDto = new PostDTO();
        ResponseEntity<PostDTO> responseEntity = postController.addPost(postDto, request);
         
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
	
	@Test
    public void testValidAddEmployee() 
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(postServiceImpl.addPost(any(Post.class))).thenReturn(new Post());
         
        PostDTO postDto = new PostDTO();
        ResponseEntity<PostDTO> responseEntity = postController.addPost(postDto, request);
        
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
    }
	
}
