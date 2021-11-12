package com.revature.services;

import com.revature.models.Profile;
import org.springframework.stereotype.Service;


public interface ProfileService {
    Profile login(String email, String password);

    Profile getProfileById(int pid);

}
