package com.revature.services;

import com.revature.models.Post;
import com.revature.models.Profile;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface PostService {
    public Post addPost(Post post);
    public List<Post> getAllPostsbyid(int page);
    public List<Post> getAllPosts();
    public List<Post> getFollowerPostsByProfile(Profile profile, int page);

}
