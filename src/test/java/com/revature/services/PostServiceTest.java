package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import com.revature.utilites.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {

    private static final Logger logger = LogManager.getLogger(PostServiceTest.class);

    @Mock
    PostRepo postRepo;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void initMock() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void setup() {
        try(Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            char[] buf = new char[1400];
            int i = new FileReader("src/test/resources/post-setup.sql").read(buf);
            if (i==0) System.exit(i);
            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
            transaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        when(postService.getAllPosts()).thenReturn(postsListTemp);
        int actual = postService.getAllPosts().size();

        assertEquals(expected, actual);
    }
}
