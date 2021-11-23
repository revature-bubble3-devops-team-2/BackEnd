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
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    public ProfileRepo profileRepo;

    /**
     * processes login request from profile controller
     * @param username
     * @param password
     * @return a user profile
     */
    public Profile login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return null;
        }
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
            profile.setPasskey(SecurityUtil.hashPassword(profile.getPasskey()));
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

    /**
     * initiates a profile lookup by username in ProfileRepo
     * @param username
     * @return
     */
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
        if (targetProfile!=null) {
            if (profile.getEmail()!=null) targetProfile.setEmail(profile.getEmail());
            if (profile.getFirstName()!=null) targetProfile.setFirstName(profile.getFirstName());
            if (profile.getLastName()!=null) targetProfile.setLastName(profile.getLastName());
            if (profile.getPasskey()!=null) targetProfile.setPasskey(profile.getPasskey());
            return profileRepo.save(targetProfile);
        }else{
            return null;
        }
    }

    /**
     * Calls ProfileRepo to remove a profile from following by email
     * @param profile profile of user initiating request
     * @param email email of profile to be removed
     * @return profile, null if unsuccessful
     */
    @Override
    public Profile removeFollowByEmail(Profile profile, String email) {
        Profile unfollow = profileRepo.getProfileByEmail(email);
        if(profile!=null){
            List<Profile> pList = profile.getFollowing();
            if(pList.contains(unfollow)){
                pList.remove(unfollow);
                profile.setFollowing(pList);
            }
                profileRepo.save(profile);
                return profile;
            }else{
                log.info("Unable to remove follow");
            }
        return null;
        }

    /**
     * Calls ProfileRepo to add a profile o following by email
     * @param profile profile of user initiating request
     * @param email email of profile to be removed
     * @return profile, null if unsuccessful
     */
    @Override
    public Profile addFollowerByEmail(Profile profile, String email) {
        List<Profile> pList = new ArrayList<>(profile.getFollowing());
        Profile followed = profileRepo.getProfileByEmail(email);
        if (followed != null && !followed.equals(profile))
        {
            if(!pList.contains(followed)) {
                pList.add(followed);
            }
            profile.setFollowing(pList);
            profileRepo.save(profile);
            return profile;
        }
        return null;
    }
//    Code misbehaving, this will need to be added in future sprint
//    /**
//     * Tests if newly followed profile is also following user
//     * @param user user submitting request
//     * @param target profile now being followed
//     * @return true if user is being followed by target
//     */
//    private boolean TargetFollowingUser(Profile user, Profile target){
//        List<Profile> pList = new ArrayList<>(target.getFollowing());
//        return pList.contains(user);
//    }
//
//    /**
//     * adds profile to friend lists of both profiles
//     * @param user user submitting request
//     * @param followed profile user now following
//     */
//    private void addFriend(Profile user, Profile followed){
//        List<Profile> userFriends = new ArrayList<>(user.getFollowing());
//        List<Profile> followedFriends = new ArrayList<>(user.getFollowing());
//        if(!userFriends.contains(followed)){
//            userFriends.add(followed);
//            user.setFollowing(userFriends);
//            profileRepo.save(user);
//        }
//        if(!followedFriends.contains(followed)){
//            followedFriends.add(followed);
//            user.setFollowing(followedFriends);
//            profileRepo.save(user);
//        }
//    }
//
//    /**
//     * removes profile from friend lists of both profiles
//     * @param user user submitting request
//     * @param followed profile user no longer following
//     */
//    private void removeFriend(Profile user, Profile followed){
//        List<Profile> userFriends = new ArrayList<>(user.getFollowing());
//        List<Profile> followedFriends = new ArrayList<>(user.getFollowing());
//        if(!userFriends.contains(followed)){
//            userFriends.add(followed);
//            user.setFollowing(userFriends);
//            profileRepo.save(user);
//        }
//        if(!followedFriends.contains(followed)){
//            followedFriends.add(followed);
//            user.setFollowing(followedFriends);
//            profileRepo.save(user);
//        }
//    }
}

