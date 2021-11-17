package com.revature.repositories;


import com.revature.models.Post;
import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    @Modifying
    @Query("update post u set u.likes = :likes where u.post_id = :id")
    void updateLikes(@Param(value = "id") int psid, @Param(value = "likes") List<Profile> likes);
}
