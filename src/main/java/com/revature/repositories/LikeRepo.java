package com.revature.repositories;

import com.revature.models.Like;
import com.revature.models.LikeId;
import com.revature.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like, LikeId> {
    long countByPost(Post post);
}
