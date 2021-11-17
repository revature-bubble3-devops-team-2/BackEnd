package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;

import java.util.List;

public interface PostService {
    public Post addPost(Post post);
    public List<Post> getAllPosts();
    public Post likePost(Profile profile, Post post);
    public int likeDelete(Profile profile, Post post);
    public int likeGet(Profile profile, Post post);
    public Post likeFindByID(Profile profile, Post post);
}
