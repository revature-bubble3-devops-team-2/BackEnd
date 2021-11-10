package com.revature.repositories;

import com.revature.models.Post;
import com.revature.models.Profile;
import com.revature.utilites.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PostRepoTest {

    private static final Logger logger = LogManager.getLogger(PostRepoTest.class);

    @Autowired
    PostRepo postRepo;

//    @BeforeAll
//    static void setup() {
//        try (Session session = HibernateUtil.getSession()) {
//            Transaction transaction = session.beginTransaction();
//            char[] buf = new char[1400];
//            int i = new FileReader("src/test/resources/post-setup.sql").read(buf);
//            if (i==0) System.exit(i);
//            session.createSQLQuery(String.valueOf(buf).trim()).executeUpdate();
//            transaction.commit();
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
//    }

    @Test
    void addPost() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post temp = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
        Post check = postRepo.save(temp);
        assertEquals(check, temp);
    }
}
