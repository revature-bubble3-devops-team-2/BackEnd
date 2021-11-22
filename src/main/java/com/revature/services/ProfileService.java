package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    Profile login(String email, String password);
    Profile addNewProfile(Profile profile);
    Profile getProfileByEmail(Profile profile);
    Profile getProfileByPid(Integer pid);
    Profile updateProfile(Profile profile);
}
