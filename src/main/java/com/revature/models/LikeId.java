package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
public class LikeId implements Serializable {

    @Autowired
    @JoinColumn(name = "post_id")
    private Post post;

    @Autowired
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
