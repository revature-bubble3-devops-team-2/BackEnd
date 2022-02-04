package com.revature.repositories;


import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, Integer> {
    Profile getProfileByEmail(String email);
    Profile getProfileByPid(Integer pid);
    Profile getProfileByUsername(String username);
    
}
