package com.revature.repositories;

import com.revature.models.Post;
import com.revature.models.Profile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PostRepoTest {

    private static final Logger logger = LogManager.getLogger(PostRepoTest.class);

    @Autowired
    PostRepo postRepo;

    @Test
    void addPost() {
        Profile tempProfile = new Profile(2, "profile2", "22", "Two", "LastTwo", "Email2");
        Post temp = new Post(3, tempProfile, "Hello World", null, Timestamp.valueOf(LocalDateTime.now()));
        Post check = postRepo.save(temp);
        assertEquals(check, temp);
    }
}
