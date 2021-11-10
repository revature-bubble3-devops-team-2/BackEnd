package com.revature.services;

import com.revature.daos.UserDao;
import com.revature.daos.UserDaoImpl;
import com.revature.models.AuthorizationSession;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao = new UserDaoImpl();

    @Override
    public User register(User user, String password) throws NoSuchAlgorithmException {
        return userDao.register(user, password);
    }
}
