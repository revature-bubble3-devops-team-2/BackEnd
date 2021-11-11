package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.PostRepo;
import com.revature.repositories.PostRepoTest;
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

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PostServiceTest {

    private static final Logger logger = LogManager.getLogger(PostRepoTest.class);

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
}
