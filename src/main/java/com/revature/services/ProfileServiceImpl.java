package com.revature.services;

import com.revature.repositories.ProfileRepo;
import com.revature.models.Profile;
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
        return profileRepo.save(profile);
    }
}
