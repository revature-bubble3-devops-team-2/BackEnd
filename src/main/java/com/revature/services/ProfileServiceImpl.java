package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepo profileRepo;

    public ProfileServiceImpl() {
    }

    @Override
    public Profile addNewProfile(Profile profile) {
        try {
            profileRepo.save(profile);
            return profile;
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Profile getProfileByEmail(Profile profile) {
       try{
           profileRepo.getProfileByEmail(profile.getEmail());
           return profile;
       }catch (Exception e)
       {
           return null;
       }
    }
}
