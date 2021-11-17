package com.revature.services;

import com.revature.models.Followers;
import com.revature.models.Profile;
import org.springframework.stereotype.Service;

public interface FollowerService {

    public boolean addFollowerByProfile(Profile creator, Profile followed);
    public boolean deleteFollowerByProfile(Profile creator, Profile followed);
}
