package com.revature.services;

import com.revature.models.AuthorizationSession;
import com.revature.models.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
    public User register(User user, String password) throws NoSuchAlgorithmException;
}
