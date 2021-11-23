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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Comment comment = new Comment(2, tempProfile, tempPost, "Test", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.save(comment)).thenReturn(comment);
        assertEquals(comment, commentService.addComment(comment));
    }

    @Test
    void testAddInvalidComment(){
        Comment comment = new Comment(2, null, null, "Testing 2", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.save(comment)).thenReturn(null);
        assertEquals(null, commentService.addComment(comment));
    }

    @Test
    void testGetComments(){
        List<Comment> commentList = new ArrayList<>();
        Comment comment = new Comment(2, null, null, "Testing 4", Timestamp.valueOf(LocalDateTime.now()), null);
        commentList.add(comment);
        when(commentRepo.getCommentByPostPsid(24)).thenReturn(commentList);
        assertEquals(commentList, commentService.getCommentByPostPsid(24));
    }

    @Test
    void testGetInvalidCommentByPsid(){
        when(commentRepo.getCommentByPostPsid(1000)).thenReturn(null);
        assertEquals(null, commentService.getCommentByPostPsid(1000));
    }

    @Test
    void testUpdateComment(){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Comment expected = new Comment(1, null, null, "Update to this comment body test update", timestamp, null);
        Comment old = new Comment(1, null, null, "Test comment", timestamp, null);
        when(commentRepo.getCommentByCid(1)).thenReturn(old);
        when(commentRepo.save(old)).thenReturn(old);
        assertEquals(expected, commentService.updateComment(expected));
    }

    @Test
    void testDeleteComment(){
        Comment comment = new Comment(1, null, null, "Update to test delete", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.getCommentByCid(1)).thenReturn(comment);
        assertEquals(true, commentService.deleteCommentByCid(1));
    }

    @Test
    void testDeleteInvalidComment(){
        when(commentRepo.getCommentByCid(1)).thenReturn(null);
        assertEquals(false, commentService.deleteCommentByCid(1));
    }

    @Test
    void testGetCommentByCid(){
        Comment comment = new Comment(1, null, null, "Update test comment by cid", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.getCommentByCid(1)).thenReturn(comment);
        assertEquals(comment, commentService.getCommentByCid(1));
    }

    @Test
    void testGetInvalidCommentByCid(){
        when(commentRepo.getCommentByCid(1)).thenReturn(null);
        assertEquals(null, commentService.getCommentByCid(1));
    }

}
