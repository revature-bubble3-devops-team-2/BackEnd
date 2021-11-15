package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity @Table(name = "likes")
@Data
@AllArgsConstructor
public class Like {

    @EmbeddedId
    private LikeId likeId;

    @Autowired
    @MapsId("post")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Autowired
    @MapsId("profile")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    public Like () {
        super();
    }

    public Like (LikeId likeId) {
        this.likeId = likeId;
        this.post = likeId.getPost();
        this.profile = likeId.getProfile();
    }
}
