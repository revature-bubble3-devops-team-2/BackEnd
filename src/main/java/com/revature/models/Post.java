package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Component
@Entity @Table(name = "post")
@Getter @Setter @AllArgsConstructor @ToString @EqualsAndHashCode
public class Post {

    @Id
    @Column(name = "post_id")
    private int psid;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile creator;

    @Column
    private String body;

    @Column(name = "image_url")
    private String imgURL;

    @Column(name = "date_posted")
    private Timestamp datePosted;

    public Post() {
        psid = SecurityUtil.getId();
    }
}
