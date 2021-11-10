package com.revature.daos;

import com.revature.models.AuthorizationSession;
import com.revature.models.User;

import java.security.NoSuchAlgorithmException;

public interface UserDao {
    public User register(User user, String password) throws NoSuchAlgorithmException;
}
