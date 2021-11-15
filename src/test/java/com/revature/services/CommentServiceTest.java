package com.revature.services;

import com.revature.models.Comment;
import com.revature.models.Post;
import com.revature.models.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {

    @Mock
    Comment comment;

    @InjectMocks
    CommentService commentService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPost() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post temp = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
        
    }
}
