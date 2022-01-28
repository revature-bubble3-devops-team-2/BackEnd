package com.revature.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.util.*;
import java.util.LinkedHashSet;



@Component
@Entity
@Table(name = "profile")
@Getter @Setter @AllArgsConstructor
public class Profile {
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

    @Column(name = "following")
    @ManyToMany
    @JsonIgnore
    private List<Profile> following = new LinkedList<>();
    
    @ManyToMany(mappedBy="members")
    @JsonIgnore
    private Set<Group> groups;

    public Profile() {
        this.pid = SecurityUtil.getId();
    }

    public Profile(int pid, String username, String passkey, String firstName, String lastName, String email) {
        this.pid = pid;
        this.username = username;
        this.passkey = passkey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Profile(String username, String passkey, String firstName, String lastName, String email) {
        this();
        this.username = username;
        this.passkey = passkey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profile profile = (Profile) o;

        if (pid != profile.pid) return false;
        if (!username.equals(profile.username)) return false;
        if (!passkey.equals(profile.passkey)) return false;
        if (!firstName.equals(profile.firstName)) return false;
        if (!lastName.equals(profile.lastName)) return false;
        if (!email.equals(profile.email)) return false;
        return Objects.equals(following, profile.following);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, username, passkey, firstName, lastName, email);
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

    public boolean isIncomplete() {
        return this.username == null || this.passkey == null || this.firstName == null || this.lastName == null ||
                this.email == null || this.following == null ||  this.username.isEmpty() ||
                this.passkey.isEmpty() || this.firstName.isEmpty() || this.lastName.isEmpty() || this.email.isEmpty() ||
                this.pid < 100;
    }

}

