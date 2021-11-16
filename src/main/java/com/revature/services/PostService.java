package com.revature.services;

import com.revature.models.Post;

import java.util.List;

public interface PostService {
    Post addPost(Post post);
    List<Post> getAllPosts();

}
