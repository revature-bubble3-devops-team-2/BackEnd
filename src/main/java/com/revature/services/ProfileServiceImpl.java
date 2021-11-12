package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    public ProfileRepo profileRepo;

    /**
     * processes login request from profile controller
     * @param email
     * @param password
     * @return a user profile
     */
    public Profile login(String email, String password){
        Profile profile = profileRepo.findProfileByEmail(email);
        if(profile != null && SecurityUtil.isPassword(password,profile.getPasskey())){
            return profile;
        }
        return null;
    }

    /*
currently unused
    public Profile getProfileById(int pid)
    {
        return null;
    }
*/
}
