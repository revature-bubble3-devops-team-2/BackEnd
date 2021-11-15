package com.revature.services;

import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.repositories.LikeRepo;
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
    void testLikePost(){
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        LikeId temp = new LikeId(new Post(3, tempProfile, "Hello World1", null, Timestamp.valueOf(LocalDateTime.now())));
        Like expected = new Like(temp);

        when(likeRepo.save(expected)).thenReturn(expected);
        Like actual = likeService.likePost(temp);

        assertEquals(expected, actual);
    }
}
