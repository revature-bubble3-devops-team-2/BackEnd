package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Profile;
import com.revature.repositories.ProfileRepo;
import com.revature.utilites.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    public ProfileRepo profileRepo;

    /**
     * processes login request fromprofile controller
     *
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
     *
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
     *
     * @param profile
     * @return profile object
     */
    @Override
    public Profile getProfileByEmail(Profile profile) {
        try {
            return profileRepo.getProfileByEmail(profile.getEmail());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets User Profile by ID in database
     *
     * @param pid
     * @return profile object from database.
     */
    @Override
    public Profile getProfileByPid(int pid) {
        return profileRepo.getProfileByPid(pid);
    }

    /**
     * initiates a profile lookup by username in ProfileRepo
     *
     * @param username
     * @return
     */
    public Profile getProfileByUsername(String username) {
        return profileRepo.getProfileByUsername(username);
    }

    /**
     *
     *
     * @param profile
     * @return updated profile if it exists otherwise return null.
     *
     *
     * @author marouanekhabbaz : add update img url;
     *
     *
     */
    @Override
    public Profile updateProfile(Profile profile) {

        Profile targetProfile;
        if(profile.getPid() <= 0) {
            targetProfile = profileRepo.getProfileByEmail(profile.getEmail());
        } else {
            targetProfile = profileRepo.getProfileByPid(profile.getPid());
        }


        if (targetProfile!=null) {
            if (profile.getEmail()!=null) targetProfile.setEmail(profile.getEmail());
            if (profile.getFirstName()!=null) targetProfile.setFirstName(profile.getFirstName());
            if (profile.getLastName()!=null) targetProfile.setLastName(profile.getLastName());
            targetProfile.setPasskey(profile.getPasskey());
            targetProfile.setVerification(profile.isVerification());
            if(profile.getImgurl() != null) {
                targetProfile.setImgurl(profile.getImgurl()) ;
            }
            if(profile.getCoverImgurl() != null) {
                targetProfile.setCoverImgurl(profile.getCoverImgurl());
            }
            return profileRepo.save(targetProfile);
        } else {
            return null;
        }
    }
    /**
     *
     *
     * @param profile
     * @return updated profile if it exists otherwise return null.
     *
     *
     * @author marouanekhabbaz : add update img url;
     *
     *
     */
    public Profile updatePassword(Profile profile) {

        Profile targetProfile;
        if(profile.getPid() <= 0) {
            targetProfile = profileRepo.getProfileByEmail(profile.getEmail());
        } else {
            targetProfile = profileRepo.getProfileByPid(profile.getPid());
        }


        if (targetProfile!=null) {

            targetProfile.setEmail(profile.getEmail());
            targetProfile.setFirstName(profile.getFirstName());
            targetProfile.setLastName(profile.getLastName());
            targetProfile.setPasskey(SecurityUtil.hashPassword(profile.getPasskey()));
            targetProfile.setVerification(profile.isVerification());
            targetProfile.setImgurl(profile.getImgurl()) ;

            return profileRepo.save(targetProfile);
        } else {
            return null;
        }
    }

    /**
     * Calls ProfileRepo to remove a profile from following by email
     *
     * @param profile profile of user initiating request
     * @param email   email of profile to be removed
     * @return profile, null if unsuccessful
     */
    @Override
    public Profile removeFollowByEmail(Profile profile, String email) {
        Profile unfollow = profileRepo.getProfileByEmail(email);
        if (profile != null) {
            List<Profile> pList = profile.getFollowing();
            if (pList.contains(unfollow)) {
                pList.remove(unfollow);
                profile.setFollowing(pList);
            }
            profileRepo.save(profile);
            return profile;
        } else {
            log.info("Unable to remove follow");
        }
        return null;
    }

    /**
     * Calls ProfileRepo to add a profile o following by email
     *
     * @param profile profile of user initiating request
     * @param email   email of profile to be removed
     * @return profile, null if unsuccessful
     */
    @Override
    public Profile addFollowerByEmail(Profile profile, String email) {

        List<Profile> pList = profile.getFollowing();


        Profile followed = profileRepo.getProfileByEmail(email);
        if (followed != null && !followed.equals(profile)) {
            if (!pList.contains(followed)) {
                pList.add(followed);
            }
            profile.setFollowing(pList);
            profileRepo.save(profile);
            return profile;
        }
        return null;
    }


    /**
     * Calls ProfileRepo to get a page of profiles
     *
     * @param page profile of user initiating request
     *
     * @return page of profiles
     */
    @Override
    public List<Profile> getAllProfilesPaginated(int page) {
        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by("username").ascending());
        Page<Profile> resultPage = profileRepo.findAll(pageable);
        if (resultPage.hasContent()) {
            return resultPage.getContent();
        }
        return new ArrayList<>();
    }


    /**
     * Calls ProfileRepo to get a list of matching profiles.
     *
     * ImageUrl and Pid are ignored when searching.
     * @param Search query without spaces
     * @return List <Profile> matching search
     */
    @Override
    public List<Profile> search(String query) {
        Profile sampleProfile = new Profile();
        sampleProfile.setPid(0);
        sampleProfile.setFirstName(query);
        sampleProfile.setLastName(query);
        sampleProfile.setUsername(query);
        sampleProfile.setEmail(query);

        ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withIgnorePaths("pid")
                .withIgnorePaths("following")
                .withIgnorePaths("groups")
                .withIgnorePaths("imgurl")
                .withIgnorePaths("passkey")
                .withIgnorePaths("verification");

        Example<Profile> example = Example.of(sampleProfile, ignoringExampleMatcher);
        return profileRepo.findAll(example);
    }

    @Override
    public List<Profile> getFollowers(int id) {
        return profileRepo.getFollowers(id);
    }



}