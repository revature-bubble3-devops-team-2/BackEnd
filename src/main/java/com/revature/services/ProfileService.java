package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    public Profile login(String username, String password);
    public Profile addNewProfile(Profile profile);
    public Profile getProfileByEmail(String email);
    public Profile getProfileByPid(Integer pid);
    public Profile updateProfile(Profile profile);
<<<<<<< HEAD
    public Profile addFollowerByEmail(Profile profile, String email);
    public Profile removeFollowByEmail(Profile profile, String email);
=======
    public Profile removeFollowByEmail(Profile profile, String email);
    public Profile addFollowerByEmail(Profile profile, String email);
>>>>>>> 4161c9537f5057c89edc963669dffb607607079e
}
