package com.revature.services;

import com.revature.models.Post;
import com.revature.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepo postRepo;

    @Override
    public Post addNewPost(Post post) {
        try {
            postRepo.save(post);
            return post;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int deletePost(Post post) {
        return 0;
    }
}
