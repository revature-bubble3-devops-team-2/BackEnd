package com.revature.models;

import com.revature.utilites.SecurityUtil;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Profile{" +
                "pid=" + pid +
                ", username='" + username + '\'' +
                ", passkey='" + passkey + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return pid == profile.pid && Objects.equals(username, profile.username) && Objects.equals(passkey, profile.passkey) && Objects.equals(firstName, profile.firstName) && Objects.equals(lastName, profile.lastName) && Objects.equals(email, profile.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, username, passkey, firstName, lastName, email);
    }
}
