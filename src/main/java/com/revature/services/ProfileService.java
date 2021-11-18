package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
<<<<<<< HEAD
    public Profile login(String username, String password);
    public Profile addNewProfile(Profile profile);
    public Profile getProfileByEmail(Profile profile);
    public Profile getProfileByPid(Integer pid);
    public Profile getProfileByUsername(String username);
    public Profile updateProfile(Profile profile);
    public Profile removeFollowByEmail(Profile profile, String email);
=======
    Profile login(String email, String password);
    Profile addNewProfile(Profile profile);
    Profile getProfileByEmail(Profile profile);
    Profile getProfileByPid(Integer pid);
    Profile updateProfile(Profile profile);
>>>>>>> main
}
