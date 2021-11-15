package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity @Table(name = "like")
@Data
@AllArgsConstructor
public class Like {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "post_id", column = @Column(name = "post_id", nullable = false)),
            @AttributeOverride(name = "profile_id", column = @Column(name = "profile_id", nullable = false))
    })
    private LikeId likeId;

//    @Autowired
//    @OneToMany
//    @JoinColumn(name = "post_id", nullable = false)
//    private Post post;
//
//    @Autowired
//    @OneToMany
//    @JoinColumn(name = "profile_id", nullable = false)
//    private Profile profile;
}
