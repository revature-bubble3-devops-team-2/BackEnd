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
    private static final String USERNAME = "dummyUsername";
    private static final String PASSWORD = "dummyPassword";
    private static final String NAME = "dummyName";
    private static final String EMAIL = "dummy@email.com";
    private Profile creator = new Profile();
    private Profile liker = new Profile();

    private static final String BODY = "This is some body text. \n With some various characters. $%^Z&*())()@!!#";
    private static final String IMG = "https://this.is.a.url.supposedly.net.com.edu.gov";
    private Post post = new Post();

    private static Timestamp getTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    @Mock
    PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
<<<<<<< HEAD
//    @Test
//    void testAddPost() {
//        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
//        Post temp = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
//        Post check = postService.addPost(temp);
//        assertEquals(check, temp);
//    }
=======
    void initMock() {
        MockitoAnnotations.openMocks(this);
        creator = new Profile(USERNAME, PASSWORD, NAME, NAME, EMAIL);
        liker = new Profile(USERNAME, PASSWORD, NAME, NAME, EMAIL);
        post = new Post(creator, BODY, IMG, getTime());
    }

    @Test
    void testAddValidPost() {
        Post check = postService.addPost(post);
        assertEquals(check, post);
    }
>>>>>>> main

    @Test
    void testAddNullPost() {
        assertAll (
            () -> assertNull(postService.addPost(null)),
            () -> assertNull(postService.addPost(new Post())),
            () -> assertNull(postService.addPost(new Post(13, null, null,
                    null,Timestamp.valueOf(LocalDateTime.now())))),
            () -> assertNull(postService.addPost(new Post(134, new Profile(), null,
                    null, null)))
        );
    }

<<<<<<< HEAD
//    @Test
//    void testGetAllPosts() {
//        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
//        Post tempPost1 = new Post(3, tempProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()));
//        Post tempPost2 = new Post(4, tempProfile, "Hello World2", null, Timestamp.valueOf(LocalDateTime.now()));
//        Post tempPost3 = new Post(5, tempProfile, "Hello World3", null, Timestamp.valueOf(LocalDateTime.now()));
//
//        List<Post> postsListTemp = new ArrayList<>();
//        postsListTemp.add(tempPost1);
//        postsListTemp.add(tempPost2);
//        postsListTemp.add(tempPost3);
//        int expected = 3;
//
//        postService.addPost(tempPost1);
//        postService.addPost(tempPost2);
//        postService.addPost(tempPost3);
//        when(postRepo.findAll()).thenReturn(postsListTemp);
//        int actual = postService.getAllPosts().size();
//
//        assertEquals(expected, actual);
//    }
=======
    @Test
    void testGetAllPosts() {
        List<Post> expected = new ArrayList<>();
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime()));
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime()));
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime()));

        postService.addPost(expected.get(0));
        postService.addPost(expected.get(1));
        postService.addPost(expected.get(2));
        when(postRepo.findAll()).thenReturn(expected);
        List<Post> actual = postService.getAllPosts();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0), actual.get(0)),
                () -> assertEquals(expected.get(1), actual.get(1)),
                () -> assertEquals(expected.get(2), actual.get(2))
        );
    }

    @Test
    void testLikePost() {
        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        Profile actual = postService.likePost(liker, post);

        assertEquals(liker, actual);
    }

    @Test
    void testLikeDelete() {
        Set<Profile> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(liker);
        post.setLikes(tempLikesSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        int actual = postService.likeDelete(liker, post);

        assertEquals(1, actual);
    }

    @Test
    void testLikeGet() {
        Set<Profile> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(liker);
        tempLikesSet.add(creator);
        post.setLikes(tempLikesSet);
        int expected = tempLikesSet.size();

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        int actual = postService.likeGet(liker, post);

        assertEquals(expected, actual);
    }

    @Test
    void testLikeFindID() {
        Set<Profile> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(liker);
        post.setLikes(tempLikesSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        Profile actual = postService.likeFindByID(liker, post);

        assertEquals(liker, actual);
    }
>>>>>>> main
}
