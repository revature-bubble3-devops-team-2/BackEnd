package com.revature.data;
import com.revature.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileData extends JpaRepository<Profile, Integer> {
    public Profile getProfileByPid(Integer pid);


}
