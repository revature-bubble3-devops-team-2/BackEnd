package com.revature.services;

import com.revature.models.Profile;
import org.springframework.stereotype.Service;


public interface ProfileService {
    Profile login(String email, String password);

    Profile getProfileByCredential(Profile profile);

    Profile getProfileById(int pid);

    Profile getProfileByEmail(String email);


}
