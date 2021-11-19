package com.revature.repositories;

import com.revature.models.Followers;
import com.revature.models.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerRepo extends JpaRepository<Followers, Integer> {
    public List<Followers> getFollowersByProfile(Profile profile);
}
