package com.revature.services;

import com.revature.models.Profile;

public interface ProfileService {
    Profile getProfileByCredential(String email, String password);

    Profile getProfileById(int pid);

    Profile getProfileByEmail(String email);
}
