package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;


class PostServiceTest {
    private static final String USERNAME = "dummyUsername";
    private static final String PASSWORD = "dummyPassword";
    private static final String NAME = "dummyName";
    private static final String EMAIL = "dummy@email.com";
    private static boolean VERIFICATION = true;
    private Profile creator = new Profile();
    private Profile liker = new Profile();

    private static final String BODY = "This is some body text. \n With some various characters. $%^Z&*())()@!!#";
    private static final String IMG = "https://this.is.a.url.supposedly.net.com.edu.gov";
    private Post post = new Post();
    private Post badPost = new Post();

    private static Timestamp getTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    @Mock
    PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
        creator = new Profile(USERNAME, PASSWORD, NAME, NAME, EMAIL, VERIFICATION);
        liker = new Profile(USERNAME, PASSWORD, NAME, NAME, EMAIL, VERIFICATION);
        post = new Post(creator, BODY, IMG, getTime(), null);
    }

    @Test
    void testAddValidPost() {
        Post check = postService.addPost(post);
        assertEquals(check, post);
    }

    @Test
    void testAddNullPost() {
        assertAll (

            () -> assertNull(postService.addPost(null)),
            () -> assertNull(postService.addPost(new Post())),
            () -> assertNull(postService.addPost(new Post(13, null, null, null,Timestamp.valueOf(LocalDateTime.now()), null))),
            () -> assertNull(postService.addPost(new Post(134, new Profile(), null, null, null, null)))

        );
    }

    @Test
    void testGetAllPosts() {
        List<Post> expected = new ArrayList<>();
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime(), null));
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime(), null));
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime(), null));

        when(postRepo.findAllByGroupIsNull()).thenReturn(expected);
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
        Set<Integer> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(creator.getPid());
        post.setLikes(tempLikesSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        Profile actual = postService.likePost(liker, post);
        Profile actual2 = postService.likePost(liker, badPost);
        Profile actual3 = postService.likePost(creator, post);

        assertEquals(liker, actual);
        assertNull(actual2);
        assertNull(actual3);
    }

    @Test
    void testLikeDelete() {
        Set<Integer> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(liker.getPid());
        post.setLikes(tempLikesSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        int actual = postService.likeDelete(liker, post);
        int actual2 = postService.likeDelete(creator, post);
        int actual3 = postService.likeDelete(liker, badPost);

        assertEquals(1, actual);
        assertEquals(-1, actual2);
        assertEquals(-1, actual3);
    }

    @Test
    void testLikeGet() {
        Set<Integer> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(liker.getPid());
        tempLikesSet.add(creator.getPid());
        post.setLikes(tempLikesSet);
        int expected = tempLikesSet.size();

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        int actual = postService.likeGet(post);

        assertEquals(expected, actual);
    }

        @Test
    void testLikeFindID() {
        Set<Integer> tempLikesSet = new LinkedHashSet<>();
        tempLikesSet.add(liker.getPid());
        post.setLikes(tempLikesSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        Profile actual = postService.likeFindByID(liker, post);
        Profile actual2 = postService.likeFindByID(creator, post);
        Profile actual3 = postService.likeFindByID(liker, badPost);

        assertEquals(liker, actual);
        assertNull(actual2);
        assertNull(actual3);
    }

    @Test
    void testBookmarkFindID() {
        Set<Integer> tempBookmarksSet = new LinkedHashSet<>();
        tempBookmarksSet.add(liker.getPid());
        post.setBookmarks(tempBookmarksSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        Profile actual = postService.bookmarkFindByID(liker, post);
        Profile actual2 = postService.bookmarkFindByID(creator, post);
        Profile actual3 = postService.bookmarkFindByID(liker, badPost);

        assertEquals(liker, actual);
        assertNull(actual2);
        assertNull(actual3);
    }

    @Test
    void testBookmarkPost() {
        Set<Integer> tempBookmarksSet = new LinkedHashSet<>();
        tempBookmarksSet.add(creator.getPid());
        post.setBookmarks(tempBookmarksSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        Profile actual = postService.bookmarkPost(liker, post);
        Profile actual2 = postService.bookmarkPost(liker, badPost);
        Profile actual3 = postService.bookmarkPost(creator, post);

        assertEquals(liker, actual);
        assertNull(actual2);
        assertNull(actual3);
    }

    @Test
    void testBookmarkDelete() {
        Set<Integer> tempBookmarksSet = new LinkedHashSet<>();
        tempBookmarksSet.add(liker.getPid());
        post.setBookmarks(tempBookmarksSet);

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        int actual = postService.bookmarkDelete(liker, post);
        int actual2 = postService.bookmarkDelete(creator, post);
        int actual3 = postService.bookmarkDelete(liker, badPost);

        assertEquals(1, actual);
        assertEquals(-1, actual2);
        assertEquals(-1, actual3);
    }

    @Test
    void testBookmarkGet() {
        Set<Integer> tempBookmarksSet = new LinkedHashSet<>();
        tempBookmarksSet.add(liker.getPid());
        tempBookmarksSet.add(creator.getPid());
        post.setBookmarks(tempBookmarksSet);
        int expected = tempBookmarksSet.size();

        when(postRepo.findById(post.getPsid())).thenReturn(Optional.of(post));
        int actual = postService.bookmarkGet(post);

        assertEquals(expected, actual);
    }
    
    @Test
    void testAllBookMarksByCreator() {
        Set<Integer> tempBookmarksSet = new LinkedHashSet<>();
        tempBookmarksSet.add(liker.getPid());
        tempBookmarksSet.add(creator.getPid());
        List<Post> expected = new ArrayList<>();
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime(), null));
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime(), null));
        expected.add(new Post(creator, USERNAME, PASSWORD, getTime(), null));

        expected.get(0).setBookmarks(tempBookmarksSet);
        expected.get(1).setBookmarks(tempBookmarksSet);
        expected.get(2).setBookmarks(tempBookmarksSet);

        when(postRepo.findAll()).thenReturn(expected);
        System.out.println(post.getBookmarks());
        List<Post> actual = postService.allBookMarksByCreator(creator);
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(expected.size(), actual.size()),
                () -> assertEquals(expected.get(0), actual.get(0)),
                () -> assertEquals(expected.get(1), actual.get(1)),
                () -> assertEquals(expected.get(2), actual.get(2))
        );
    }

}



