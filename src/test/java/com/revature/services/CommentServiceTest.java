package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.CommentRepo;

class CommentServiceTest {
	
	private Profile tempProfile = new Profile();
	private Post tempPost = new Post();
	private Comment comment = new Comment();
	private Comment reply = new Comment();
	private Comment reply2 = new Comment();
	private List<Comment> comments = new ArrayList<>();
	private List<Comment> replies = new ArrayList<>();
	private Pageable pageable = PageRequest.of(0, 5, Sort.by("dateCreated").ascending());
	
	
	
    @Mock
    CommentRepo commentRepo;

    @InjectMocks
    CommentServiceImpl commentService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        comments = new ArrayList<>();
        replies = new ArrayList<>();
        tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2", true);
        tempPost = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()), null);
        comment = new Comment(14, tempProfile, tempPost, "Test Comment", Timestamp.valueOf(LocalDateTime.now()), null);
        reply = new Comment(16, tempProfile, tempPost, "Test Reply", Timestamp.valueOf(LocalDateTime.now()), comment);
        reply2 = new Comment(17, tempProfile, tempPost, "Test Reply 2", Timestamp.valueOf(LocalDateTime.now()), comment);
    }
    
    @Test
    void testAddComment() {
        when(commentRepo.save(comment)).thenReturn(comment);
        assertEquals(comment, commentService.addComment(comment));
    }

    @Test
    void testAddInvalidComment(){
        comment = new Comment(null, null, "Testing 2", Timestamp.valueOf(LocalDateTime.now()), null);
        when(commentRepo.save(comment)).thenReturn(null);
        assertNull(commentService.addComment(comment));
    }

    @Test
    void testGetComments(){
        comments.add(comment);
        when(commentRepo.getCommentsByPostPsid(3)).thenReturn(comments);
        assertEquals(comments, commentService.getCommentsByPostPsid(3));
    }
    
    @Test
    void testGetNoComments(){
    	when(commentRepo.getCommentsByPostPsid(566548189)).thenReturn(comments);
    	assertEquals(new ArrayList<>(), commentService.getCommentsByPostPsid(566548189));
    }
    
    @Test
    void testGetCommentsPaginated() {
    	comments.add(comment);
    	Page<Comment> commentPage = new PageImpl<Comment>(comments);
    	when(commentRepo.getCommentsByPostPsid(pageable,3)).thenReturn(commentPage);
    	assertEquals(comments, commentService.getCommentsByPostPsidPaginated(3, 1));
    }
    
    @Test
    void testGetNoCommentsPaginated() {
    	Page<Comment> commentPage = new PageImpl<Comment>(comments);
    	when(commentRepo.getCommentsByPostPsid(pageable,3)).thenReturn(commentPage);
    	assertEquals(new ArrayList<>(), commentService.getCommentsByPostPsidPaginated(3, 1));
    }
    
    @Test
    void testGetOriginalComments(){
    	comments.add(comment);
    	comments.add(reply);
    	List<Comment> noReplies = new ArrayList<>();
    	noReplies.add(comment);
    	when(commentRepo.getCommentsByPostPsid(3)).thenReturn(comments);
    	assertEquals(noReplies, commentService.getOriginalCommentsByPostPsid(3));
    }
    
    @Test
    void testGetNoOriginalComments(){
    	when(commentRepo.getCommentsByPostPsid(566548189)).thenReturn(comments);
    	assertEquals(new ArrayList<>(), commentService.getCommentsByPostPsid(566548189));
    }
    
    @Test
    void testGetOriginalCommentsPaginated() {
    	comments.add(comment);
    	comments.add(reply);
    	List<Comment> noReplies = new ArrayList<>();
    	noReplies.add(comment);
    	Page<Comment> commentPage = new PageImpl<Comment>(comments);
    	when(commentRepo.getCommentsByPostPsid(pageable, 3)).thenReturn(commentPage);
    	assertEquals(noReplies, commentService.getOriginalCommentsByPostPsidPaginated(3, 1));
    }
    
    @Test
    void testGetNoOriginalCommentsPaginated() {
    	comments.add(reply);
    	Page<Comment> commentPage = new PageImpl<Comment>(comments);
    	when(commentRepo.getCommentsByPostPsid(pageable, 3)).thenReturn(commentPage);
    	assertEquals(new ArrayList<>(), commentService.getOriginalCommentsByPostPsidPaginated(3, 1));
    }
    
    @Test
    void testGetReplies(){
    	comments.add(comment);
    	comments.add(reply);
    	comments.add(reply2);
    	replies.add(reply);
    	replies.add(reply2);
    	when(commentRepo.getCommentsByPostPsidAndPrevious(3,14)).thenReturn(replies);
    	assertEquals(replies, commentService.getCommentsByPostPsidAndPrevious(3, 14));
    }
    
    @Test
    void testGetNoReplies(){
    	when(commentRepo.getCommentsByPostPsidAndPrevious(3,14)).thenReturn(replies);
    	assertEquals(replies, commentService.getCommentsByPostPsidAndPrevious(3, 14));
    }
    
    @Test
    void testGetRepliesPaginated() {
    	replies.add(reply);
    	replies.add(reply2);
    	Page<Comment> commentPage = new PageImpl<Comment>(replies);
    	when(commentRepo.getCommentsByPostPsidAndPrevious(pageable, 3, 14)).thenReturn(commentPage);
    	assertEquals(replies, commentService.getCommentsByPostPsidAndPreviousPaginated(3, 14, 1));
    }
    
    @Test
    void testGetNoRepliesPaginated() {
    	Page<Comment> commentPage = new PageImpl<Comment>(comments);
    	when(commentRepo.getCommentsByPostPsidAndPrevious(pageable, 3, 14)).thenReturn(commentPage);
    	assertEquals(new ArrayList<>(), commentService.getCommentsByPostPsidAndPreviousPaginated(3, 14, 1));
    }
    

    @Test
    void testGetCommentByCid(){
        when(commentRepo.getCommentByCid(14)).thenReturn(comment);
        assertEquals(comment, commentService.getCommentByCid(14));
    }

    @Test
    void testGetNoCommentByCid(){
        when(commentRepo.getCommentByCid(12342)).thenReturn(null);
        assertNull(commentService.getCommentByCid(12342));
    }
    

}
