package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    public ProfileRepo profileRepo;
    public ProfileService profileService;

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
            String hashedPWD = SecurityUtil.hashPassword(profile.getPasskey());
            profile.setPasskey(hashedPWD);
            return profileRepo.save(profile);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets User Profile by Email in the Database
     * @param email
     * @return a big fat load of profile object
     */
    @Override
    public Profile getProfileByEmail(String email) {
       try{
           return profileRepo.getProfileByEmail(email);
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

    public Profile getProfileByUsername(String username) {
        return profileRepo.getProfileByUsername(username);
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

    @Override
    public Profile removeFollowByEmail(Profile profile, String email) {
        Profile targetProfile = profileRepo.getProfileByPid(profile.getPid());
        log.info("target acquired: "+targetProfile);
        Profile unfollow = profileRepo.getProfileByEmail(email);
        if(targetProfile!=null){
            targetProfile.getFollowing().remove(unfollow);
            return profileRepo.save(targetProfile);
        }else{
            return null;
        }
    }

    @Override
    public Profile addFollowerByEmail(Profile profile, String email) {
        Profile followed = profileService.getProfileByEmail(email);
        System.out.println("Followed: " + followed);

        System.out.println("Before adding Followers: "+ profile.getFollowing());

        profile.getFollowing().add(followed);

        System.out.println("After adding Followers: "+ profile.getFollowing());

        return null;
    }
}
