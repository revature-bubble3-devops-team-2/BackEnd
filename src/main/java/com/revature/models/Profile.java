package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.Data;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Objects;

@Component
@Entity
@Table(name = "profiles", schema = "bubble")
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private int pid;

    @Column(name = "username")
    private String username;

    @Column(name = "passkey")
    private String passkey;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
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
