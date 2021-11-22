package com.revature.repositories;


import com.revature.models.Post;
import com.revature.models.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> getPostsByCreator(Pageable pageable, int profileID );
    List<Post>findTop3ByCreator(Profile profile, Sort sort);

}
