package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
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
        Profile unfollow = profileRepo.getProfileByEmail(email);
        List<Profile> pList = new ArrayList<>(profile.getFollowing());
        for(Object p : pList){
            System.out.println(p);
        }
//        int profileId = profile.getPid();
//        for(Object p : profile.getFollowing()){
//            System.out.println(p.toString());
//            System.out.println("found one");
//        }
//        if(profile!=null && profile.getFollowing().remove(unfollow)){
            if(profile!=null && pList.remove(unfollow)){
                return profileRepo.save(profile);
            }else{
                log.info("Unable to remove follow");
                return null;
            }
        }

    @Override
    public Profile addFollowerByEmail(Profile profile, String email) {
        List<Profile> pList = new ArrayList<>(profile.getFollowing());
        Profile followed = profileRepo.getProfileByEmail(email);
        pList.add(followed);
        System.out.println("List 1 after add");
        for(Object p : pList){
            System.out.println("Profile:");
            System.out.println(p);
        }
        profile.setFollowing(pList);
//        System.out.println("List 2");
//        for(Object p : pList2){
//            System.out.println("Profile:");
//            System.out.println(p);
//        }
        profileRepo.save(profile);
        return profile;
    }
}
