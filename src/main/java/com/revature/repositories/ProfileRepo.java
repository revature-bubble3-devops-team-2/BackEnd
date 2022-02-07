package com.revature.repositories;


import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    Profile getProfileByEmail(String email);
    Profile getProfileByPid(Integer pid);
    Profile getProfileByUsername(String username);
    
//    @Query(value = "select * from profile_following pf where pf.following_profile_id = :id", nativeQuery = true)
    @Query(value = "\r\n"
    		+ "select * from profile p \r\n"
    		+ "join profile_following pf on pf.profile_profile_id = p.profile_id \r\n"
    		+ "where pf.following_profile_id = :id" , nativeQuery = true)
    List< Profile > getFollowers( @Param("id") int id);
    
}
