package com.revature.services;

import com.revature.models.Profile;
import org.springframework.stereotype.Service;


public interface ProfileService {
    Profile login(String email, String password);

//    Profile getProfileById(int pid);


    public Profile addNewProfile(Profile profile);
    public Profile getProfileByEmail(Profile profile);

    public Profile getProfileByPid(Integer pid);

    public Profile updateProfile(Profile profile);

    //public Profile save(Profile profile);
}
