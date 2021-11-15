package com.revature.repositories;

import com.revature.models.Like;
import com.revature.models.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like, LikeId> {

}
