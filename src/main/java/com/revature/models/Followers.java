package com.revature.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Table(name = "followers")
@AllArgsConstructor
@Data
public class Followers {

    @EmbeddedId
    private FollowId followId;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Profile follower;
}
