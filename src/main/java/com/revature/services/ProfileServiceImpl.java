package com.revature.services;

import com.revature.
import com.revature.models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileData profileData;

    public ProfileServiceImpl() {
    }

    @Override
    public Profile addNewProfile(Profile profile) {
        try {
            profileData.save(profile);
            return profile;
        } catch (Exception e) {
            return null;
        }
    }
}
