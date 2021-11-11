package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    public Profile addNewProfile(Profile profile);

    public Profile getProfileByPid(Integer pid);
}
