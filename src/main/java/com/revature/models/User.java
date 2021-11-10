package com.revature.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import javax.validation.constraints.*;

@Component
@Entity
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int userId;
    @Email
    @Column(name = "email")
    public String email;
    @Column(name = "first_name")
    public String firstName;
    @Column(name = "last_name")
    public String lastName;
    @Column(name = "birthday")
    public Date birthday;
    @Column(name = "user_password")
    private String passwordHash;
    public String authorizationToken;

    public User() {
        super();
    }

    public User(String email, String firstName, String lastName, Date birthday) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
}
