package com.revature.services;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.CommentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @Mock
    CommentRepo commentRepo;

    @InjectMocks
    CommentServiceImpl commentService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddComment() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post tempPost = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
        Comment comment = new Comment(tempProfile, tempPost, "Test", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.save(comment)).thenReturn(comment);
        assertEquals(comment, commentService.addComment(comment));
    }

    @Test
    void testAddInvalidComment(){
        Comment comment = new Comment(null, null, "Testing 2", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.save(comment)).thenReturn(null);
        assertNull(commentService.addComment(comment));
    }

    @Test
    void testGetComments(){
        List<Comment> commentList = new ArrayList<>();
        Comment comment = new Comment(null, null, "Testing 4", Timestamp.valueOf(LocalDateTime.now()), null);
        commentList.add(comment);
        when(commentRepo.getCommentByPostPsid(24)).thenReturn(commentList);
        assertEquals(commentList, commentService.getCommentByPostPsid(24));
    }

    @Test
    void testGetInvalidCommentByPsid(){
        when(commentRepo.getCommentByPostPsid(1000)).thenReturn(null);
        assertNull(commentService.getCommentByPostPsid(1000));
    }

    @Test
    void testGetCommentByCid(){
        Comment comment = new Comment(null, null, "Update test comment by cid", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.getCommentByCid(1)).thenReturn(comment);
        assertEquals(comment, commentService.getCommentByCid(1));
    }

    @Test
    void testGetInvalidCommentByCid(){
        when(commentRepo.getCommentByCid(1)).thenReturn(null);
        assertNull(commentService.getCommentByCid(1));
    }

}
