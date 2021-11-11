package com.revature.services;

import com.revature.data.ProfileData;
import com.revature.models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileData profileData;

    public ProfileServiceImpl(){}

    @Override
    public Profile addNewProfile(Profile profile) {
        return profileData.save(profile);
    }

    @Override
    public Profile getProfileByPid(Integer pid) {
        return profileData.getProfileByPid(pid);
    }
}
