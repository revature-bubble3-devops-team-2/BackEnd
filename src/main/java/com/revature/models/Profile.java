package com.revature.models;

import com.revature.utilites.SecurityUtil;

public class Profile {
    private int pid;
    private String username;
    private String passkey;
    private String firstName;
    private String lastName;
    private String email;

    public Profile() {
        pid = SecurityUtil.getId();
    }

    public Profile(int pid, String username, String passkey, String firstName, String lastName, String email) {
        this.pid = pid;
        this.username = username;
        this.passkey = passkey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
