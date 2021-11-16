package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.*;

@Component
@Entity
@Table(name = "followers")
@Getter @Setter @AllArgsConstructor @ToString @EqualsAndHashCode
@Data
public class Followers {
    @Id
    @Column(name = "following_id")
    private int following_id;

    @Autowired
    @ManyToMany
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile creator;

    @Autowired
    @ManyToMany
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile follower;

    public Followers() {
        following_id = SecurityUtil.getId();
    }
}
