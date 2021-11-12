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
        Profile profile = profileRepo.getProfileByEmail(email);
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

    public ProfileServiceImpl() {
    }


    /**
     * Add User Profile into the Database
     * @param profile
     * @return a big fat load of object
     *
     */
    @Override
    public Profile addNewProfile(Profile profile) {
        try {
            return profileRepo.save(profile);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * Gets User Profile by Email in the Database
     * @param profile
     * @return a big fat load of profile object
     */
    @Override
    public Profile getProfileByEmail(Profile profile) {
       try{
           return profileRepo.getProfileByEmail(profile.getEmail());
       }catch (Exception e)
       {
           return null;
       }
    }
}
