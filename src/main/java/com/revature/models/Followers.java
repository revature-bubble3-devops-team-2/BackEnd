package com.revature.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Component
@Entity
@Table(name = "followers")
@IdClass(Followers.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Followers implements Serializable {

//    @EmbeddedId
//    private FollowId followId;

    @Id
    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Id
    @Autowired
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Profile follower;
}
