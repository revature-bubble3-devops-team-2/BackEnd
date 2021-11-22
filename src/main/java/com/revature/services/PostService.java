package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;

import java.util.List;

public interface PostService {
    Post addPost(Post post);
    List<Post> getAllPostsPaginated(int pageNumber);
    List<Post> getAllPosts();
    List<Post> getFollowerPostsByProfile(Profile profile, int page);

    Profile likePost(Profile profile, Post post);
    int likeDelete(Profile profile, Post post);
    int likeGet(Profile profile, Post post);
    Profile likeFindByID(Profile profile, Post post);
}
