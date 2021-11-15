package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Component
@Data
@AllArgsConstructor
public class LikeId implements Serializable {

    @Autowired
    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public LikeId () {
        super();
    }

    public LikeId (Post post) {
        this.post = post;
        this.profile = post.getCreator();
    }
}
