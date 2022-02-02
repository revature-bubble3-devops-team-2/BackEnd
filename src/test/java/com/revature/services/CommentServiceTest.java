package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.CommentRepo;

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
        when(commentRepo.getCommentsByPostPsid(24)).thenReturn(commentList);
        assertEquals(commentList, commentService.getCommentsByPostPsid(24));
    }
    
    @Test
    void testGetCommentsNoComments(){
    	List<Comment> commentList = new ArrayList<>();
    	when(commentRepo.getCommentsByPostPsid(566548189)).thenReturn(commentList);
    	assertEquals(new ArrayList<>(), commentService.getCommentsByPostPsid(566548189));
    }
    
    @Test
    void testGetOriginalCommentsPaginated() {
    	Post post = new Post(24, null, "Post Body", "", Timestamp.valueOf(LocalDateTime.now()), new LinkedHashSet<Integer>() );
    	Comment comment = new Comment(null, post, "Testing 4", Timestamp.valueOf(LocalDateTime.now()), null);
    	Comment reply = new Comment(null, post, "Testing 2", Timestamp.valueOf(LocalDateTime.now()), comment);
    	List<Comment> commentList = new ArrayList<>();
    	commentList.add(comment);
    	commentList.add(reply);
    	List<Comment> noReplies = new ArrayList<>();
    	noReplies.add(comment);
    	Page<Comment> commentPage = new PageImpl<Comment>(noReplies);
    	when(commentRepo.getCommentsByPostPsid(PageRequest.of(0, 5, Sort.by("dateCreated").descending()),24)).thenReturn(commentPage);
    	assertEquals(noReplies, commentService.getCommentsByPostPsidPaginated(24, 1));
    }
    
    @Test
    void testGetRepliesPaginated() {
    	Post post = new Post(24, null, "Post Body", "", Timestamp.valueOf(LocalDateTime.now()), new LinkedHashSet<Integer>() );
    	Comment comment = new Comment(null, post, "Testing 4", Timestamp.valueOf(LocalDateTime.now()), null);
    	Comment reply = new Comment(null, post, "Testing 2", Timestamp.valueOf(LocalDateTime.now()), comment);
    	List<Comment> commentList = new ArrayList<>();
    	commentList.add(comment);
    	commentList.add(reply);
    	List<Comment> replies = new ArrayList<>();
    	replies.add(reply);
    	Page<Comment> commentPage = new PageImpl<Comment>(replies);
    	when(commentRepo.getCommentsByPostPsid(PageRequest.of(0, 5, Sort.by("dateCreated").descending()),24)).thenReturn(commentPage);
    	assertEquals(replies, commentService.getCommentsByPostPsidPaginated(24, 1));
    }
    
    @Test
    void testGetCommentsPaginated() {
    	Comment comment = new Comment(null, null, "Testing 4", Timestamp.valueOf(LocalDateTime.now()), null);
    	List<Comment> commentList = new ArrayList<>();
    	commentList.add(comment);
    	Page<Comment> commentPage = new PageImpl<Comment>(commentList);
    	when(commentRepo.getCommentsByPostPsid(PageRequest.of(0, 5, Sort.by("dateCreated").descending()),24)).thenReturn(commentPage);
    	assertEquals(commentList, commentService.getCommentsByPostPsidPaginated(24, 1));
    }
    
    @Test
    void testGetCommentsPaginatedNoComments() {
    	List<Comment> commentList = new ArrayList<>();
    	Page<Comment> commentPage = new PageImpl<Comment>(new ArrayList<>());
    	when(commentRepo.getCommentsByPostPsid(PageRequest.of(0, 5, Sort.by("dateCreated").descending()),24)).thenReturn(commentPage);
    	assertEquals(commentList, commentService.getCommentsByPostPsidPaginated(24, 1));
    }

    @Test
    void testGetInvalidCommentByPsid(){
        when(commentRepo.getCommentsByPostPsid(1000)).thenReturn(null);
        assertNull(commentService.getCommentsByPostPsid(1000));
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
