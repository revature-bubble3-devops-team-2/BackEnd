package com.revature.data;

import com.revature.models.Post;
import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostData extends JpaRepository<Post, Integer> {
 List<Post> getPost(Post post);

}
