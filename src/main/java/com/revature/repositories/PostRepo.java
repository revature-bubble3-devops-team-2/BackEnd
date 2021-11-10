package com.revature.repositories;

import com.revature.models.Post;
import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

//    int deletePost(Post post);
//    int updatePost(Post post);
//    List<Post> getAllPosts();
//    List<Post> getPostByProfile(Profile profile);
//    List<Post> getPostByFollower(Profile profile);
//    Post getPostById(int id);
}
