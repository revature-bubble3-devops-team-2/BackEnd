package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CollectionType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class LikeId implements Serializable {

    @Column(name = "post_id")
    private Post post;

    @Column(name = "profile_id")
    private Profile profile;
}
