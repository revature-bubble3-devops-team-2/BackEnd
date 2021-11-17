package com.revature.services;

import com.revature.models.Profile;
import com.revature.repositories.FollowerRepo;
import com.revature.utilites.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    public FollowerRepo followerRepo;

    @Override
    public boolean addFollowerByProfile(Profile profile, Profile followed)
    {
        System.out.println(profile);

        return false;
    }

    @Override
    public boolean deleteFollowerById(int creator, int followed) {
        return false;
    }
}
