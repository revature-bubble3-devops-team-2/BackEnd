package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "followers", joinColumns = @JoinColumn(
            name = "profile_id", referencedColumnName = "profile_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "follower_id", referencedColumnName = "profile_id"))
    private List<Profile> followers;

    @ManyToMany(mappedBy = "followers")
    private List<Profile> following;


    @Column(name = "username",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    private String username;

    @Column(name = "passkey",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
