package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    public Profile login(String username, String password);
    public Profile addNewProfile(Profile profile);
    public Profile getProfileByEmail(Profile profile);
    public Profile getProfileByPid(Integer pid);
    public Profile getProfileByUsername(String username);
    public Profile updateProfile(Profile profile);
    public boolean addFollowerByProfile(Profile profile);
}
