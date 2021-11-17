package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Component
@Entity
@Table(name = "profile")
@Getter @Setter @AllArgsConstructor @ToString @EqualsAndHashCode
@Data
public class Profile {
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "profile_id")
    private int pid;

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

    @Column(name = "first_name",
            columnDefinition = "TEXT",
            nullable = false)
    private String firstName;

    @Column(name = "last_name",
            columnDefinition = "TEXT",
            nullable = false)
    private String lastName;

    @Column(name = "email",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    private String email;

    public Profile() {
        pid = SecurityUtil.getId();
    }

}
