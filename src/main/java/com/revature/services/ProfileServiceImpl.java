package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    public ProfileRepo profileRepo;

    public Profile getProfileByCredential(Profile profile)
    {
        return null;
    }

    public Profile login(String email, String password){
        Profile p = new Profile(0,"a",password,"Joe","Seph",email);


        return p;
    }

    public Profile getProfileById(int pid)
    {
        return null;
    }

    public Profile getProfileByEmail(String email)
    {
        return null;
    }
}
