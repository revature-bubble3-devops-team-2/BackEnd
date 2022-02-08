package com.revature.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.Post;
import com.revature.models.Profile;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> getPostsByCreator(Pageable pageable, int profileID );
    List<Post> findTop3ByCreator(Profile profile, Sort sort);
	List<Post> findAllByGroupGroupId(int groupId);


}
