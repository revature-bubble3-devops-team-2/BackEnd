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

    @Override
    public Profile updateProfile(Profile profile) {
        Profile targetProfile = profileData.getProfileByPid(profile.getPid());
        if(profile.getEmail()!=null)
            targetProfile.setEmail(profile.getEmail());
        if(profile.getFirstName()!=null)
            targetProfile.setFirstName(profile.getFirstName());
        if(profile.getLastName()!=null)
            targetProfile.setLastName(profile.getLastName());
        if(profile.getPasskey()!=null)
            targetProfile.setPasskey(profile.getPasskey());
        return profileData.save(targetProfile);

    }
}
