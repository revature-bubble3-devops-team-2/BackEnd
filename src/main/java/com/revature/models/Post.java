package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity @Table(name = "post")
@Data @AllArgsConstructor
public class Post {

    @Id
    @Column(name = "post_id", unique = true, nullable = false)
    private int psid;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile creator;

    @Column
    private String body;

    @Column(name = "image_url")
    private String imgURL;

    @Column(name = "date_posted", nullable = false)
    private Timestamp datePosted;

    @Column(name = "likes")
    @OneToMany
    private List<Profile> likes;

    public Post() {
        super();
        psid = SecurityUtil.getId();
    }

    public Post(int psid, Profile creator, String body, String imgURL, Timestamp dateposted) {
        this.psid = SecurityUtil.getId();
        this.creator = creator;
        this.body = body;
        this.imgURL = imgURL;
        this.datePosted = dateposted;
        this.likes = new ArrayList<>();
    }
}

