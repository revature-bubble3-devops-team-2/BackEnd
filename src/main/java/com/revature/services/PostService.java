package com.revature.services;

import com.revature.models.Post;

import java.util.List;

public interface PostService {
    public Post addPost(Post post);
    public List<Post> getAllPosts();
}
