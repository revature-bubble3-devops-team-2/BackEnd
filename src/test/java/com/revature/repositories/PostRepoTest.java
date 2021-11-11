package com.revature.repositories;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.services.PostServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PostRepoTest {

    private static final Logger logger = LogManager.getLogger(PostRepoTest.class);

    @Mock
    private PostRepo postRepo;

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
    void testSave(){
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post temp = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
        assertNull(postRepo.save(temp));
        assertThrows(Exception.class, postRepo.save(null));
    }

    @Test
    void testFindAll() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post tempPost1 = new Post(3, tempProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now()));
        Post tempPost2 = new Post(4, tempProfile, "Hello World2", null, Timestamp.valueOf(LocalDateTime.now()));

        List<Post> postsListTemp = new ArrayList<>();
        postsListTemp.add(tempPost1);
        postsListTemp.add(tempPost2);
        int expected = 2;

        postRepo.save(tempPost1);
        postRepo.save(tempPost2);
        when(postRepo.findAll()).thenReturn(postsListTemp);
        int actual = postRepo.findAll().size();

        assertEquals(expected, actual);
    }
}
