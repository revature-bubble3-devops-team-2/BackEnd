package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Component
@Entity
@Data
public class Profile {
    @Id
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
