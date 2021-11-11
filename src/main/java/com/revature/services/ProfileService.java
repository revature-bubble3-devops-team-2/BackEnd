package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    Profile login(String email, String password);

    Profile getProfileByCredential(Profile profile);

    Profile getProfileById(int pid);

    Profile getProfileByEmail(String email);


}
