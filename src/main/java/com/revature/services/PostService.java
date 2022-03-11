package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;

import java.util.List;

public interface PostService {
    Post addPost(Post post);

    List<Post> getAllPostsPaginated(int pageNumber);
    List<Post> getAllPosts();
    Post getPostByPsid(Integer psid);

    Profile likePost(Profile profile, Post post);
    int likeDelete(Profile profile, Post post);
    int likeGet(Post post);
    Profile likeFindByID(Profile profile, Post post);

    Profile bookmarkPost(Profile profile, Post post);
    int bookmarkDelete(Profile profile, Post post);
    int bookmarkGet(Post post);
    Profile bookmarkFindByID(Profile profile, Post post);
}