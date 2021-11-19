package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    public Profile login(String username, String password);
    public Profile addNewProfile(Profile profile);
    public Profile getProfileByEmail(String email);
    public Profile getProfileByPid(Integer pid);
    public Profile updateProfile(Profile profile);
    public Profile removeFollowByEmail(Profile profile, String email);
}
