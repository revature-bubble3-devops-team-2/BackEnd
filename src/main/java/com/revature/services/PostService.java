package com.revature.services;

import com.revature.models.Post;

public interface PostService {
    public Post addNewPost(Post post);
    public int deletePost(Post post);
}
