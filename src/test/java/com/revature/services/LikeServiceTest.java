package com.revature.services;

import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.LikeRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class LikeServiceTest {

    private static final Logger logger = LogManager.getLogger(PostServiceTest.class);

    @Mock
    LikeRepo likeRepo;

    @InjectMocks
    private LikeServiceImpl likeService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikePost(){
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        LikeId temp = new LikeId(new Post(3, tempProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now())));
        Like expected = new Like(temp);

        when(likeRepo.save(expected)).thenReturn(expected);
        Like actual = likeService.likePost(temp);

        assertEquals(expected, actual);
    }

    @Test
    void testLikeDelete(){
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        LikeId temp = new LikeId(new Post(3, tempProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now())));

        likeService.likeDelete(temp);
        likeService.likeDelete(null);

        verify(likeRepo).deleteById(temp);
        verify(likeRepo).deleteById(null);
    }
}
