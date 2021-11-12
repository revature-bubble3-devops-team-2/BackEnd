package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "profile")
@Getter @Setter @AllArgsConstructor @ToString @EqualsAndHashCode
public class Profile {

    @Id
    @Column (name = "profile_id")
    private int pid;

    @Column (name= "username")
    private String username;

    @Column(name="passkey")
    private String passkey;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")

    private String email;


    public Profile() {
        pid = SecurityUtil.getId();
    }

}
