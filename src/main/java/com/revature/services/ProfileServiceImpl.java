package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    public ProfileRepo profileRepo;

    /**
     * processes login request from profile controller
     * @param username
     * @param password
     * @return a user profile
     */
    public Profile login(String username, String password){
        Profile profile = profileRepo.getProfileByUsername(username);
        if (profile != null && SecurityUtil.isPassword(password, profile.getPasskey())) {
            return profile;
        }
        return null;
    }

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

    /**
     * Gets User Profile by ID in database
     * @param pid
     * @return profile object from database.
     */
    @Override
    public Profile getProfileByPid(Integer pid) {
        return profileRepo.getProfileByPid(pid);
    }

    /**
     *
     * @param profile
     * @return updated profile if it exists otherwise return null.
     */
    @Override
    public Profile updateProfile(Profile profile) {
            Profile targetProfile = profileRepo.getProfileByPid(profile.getPid());
            if(targetProfile!=null){
                if(profile.getEmail()!=null)
                    targetProfile.setEmail(profile.getEmail());
                if(profile.getFirstName()!=null)
                    targetProfile.setFirstName(profile.getFirstName());
                if(profile.getLastName()!=null)
                    targetProfile.setLastName(profile.getLastName());
                if(profile.getPasskey()!=null)
                    targetProfile.setPasskey(profile.getPasskey());
                return profileRepo.save(targetProfile);
            }else{
                return null;
            }


    }
}
