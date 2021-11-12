package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.utilites.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.Objects;

@Component
@Entity
@Table(name = "profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "profileId")
    private int profileId;

    @Column(name = "username",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    private String username;

    @Column(name = "passkey",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    @JsonIgnore
    private String passkey;

    @Column(name = "firstName",
            columnDefinition = "TEXT",
            nullable = false)
    private String firstName;

    @Column(name = "lastName",
            columnDefinition = "TEXT",
            nullable = false)
    private String lastName;

    @Column(name = "email",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    private String email;

}
