package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {

    @Mock
    PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddPost() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post temp = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
        Post check = postService.addPost(temp);
        assertEquals(check, temp);
    }

    @Test
    void testAddNullPost() {
        assertAll (
            () -> assertNull(postService.addPost(null)), //add null post
            () -> assertNull(postService.addPost(new Post())), //add empty post
            () -> assertNull(postService.addPost(new Post(13, null, null,
                    null,Timestamp.valueOf(LocalDateTime.now())))), //add with null profile
            () -> assertNull(postService.addPost(new Post(134, new Profile(), null,
                    null, null))) //add with null time created
        );
    }

    @Test
    void testGetAllPosts() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post tempPost1 = new Post(3, tempProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()));
        Post tempPost2 = new Post(4, tempProfile, "Hello World2", null, Timestamp.valueOf(LocalDateTime.now()));
        Post tempPost3 = new Post(5, tempProfile, "Hello World3", null, Timestamp.valueOf(LocalDateTime.now()));

        List<Post> postsListTemp = new ArrayList<>();
        postsListTemp.add(tempPost1);
        postsListTemp.add(tempPost2);
        postsListTemp.add(tempPost3);
        int expected = 3;

        postService.addPost(tempPost1);
        postService.addPost(tempPost2);
        postService.addPost(tempPost3);
        when(postRepo.findAll()).thenReturn(postsListTemp);
        int actual = postService.getAllPosts().size();

        assertEquals(expected, actual);
    }

    @Test
    void testLikePost() {
        Profile creatorProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Profile likerProfile = new Profile(1, "profile1", "11", "One", "LastOne", "Email1");
        Post tempPost = new Post(3, creatorProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()));

        when(postRepo.findById(tempPost.getPsid())).thenReturn(Optional.of(tempPost));
        Profile actual = postService.likePost(likerProfile, tempPost);

        assertEquals(likerProfile, actual);

    }

    @Test
    void testLikeDelete() {
        Profile creatorProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Profile likerProfile = new Profile(1, "profile1", "11", "One", "LastOne", "Email1");

        Set<Profile> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(likerProfile);

        Post tempPost = new Post(3, creatorProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()), tempLikesSet);
        when(postRepo.findById(tempPost.getPsid())).thenReturn(Optional.of(tempPost));
        int actual = postService.likeDelete(likerProfile, tempPost);

        assertEquals(actual, 1);
    }

    @Test
    void testLikeGet() {
        Profile creatorProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Profile likerProfile = new Profile(1, "profile1", "11", "One", "LastOne", "Email1");

        Set<Profile> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(likerProfile);
        tempLikesSet.add(creatorProfile);
        int expected = tempLikesSet.size();

        Post tempPost = new Post(3, creatorProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()), tempLikesSet);
        when(postRepo.findById(tempPost.getPsid())).thenReturn(Optional.of(tempPost));
        int actual = postService.likeGet(likerProfile, tempPost);

        assertEquals(expected, actual);
    }

    @Test
    void testLikeFindID() {
        Profile creatorProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Profile likerProfile = new Profile(1, "profile1", "11", "One", "LastOne", "Email1");

        Set<Profile> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(likerProfile);


        Post tempPost = new Post(3, creatorProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()), tempLikesSet);
        when(postRepo.findById(tempPost.getPsid())).thenReturn(Optional.of(tempPost));
        Profile actual = postService.likeFindByID(likerProfile, tempPost);

        assertEquals(likerProfile, actual);
    }
}
