package com.revature.repositories;

import com.revature.models.Followers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface FollowerRepo extends JpaRepository<Followers, Integer>{

    Followers getFollowerByFollowersID(Integer followers_id);
    Followers getFollowerByCreaterAndFollowed(Integer profile_id, Integer followed_id);
}
