package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Component
@Entity @Table(name = "post")
@Data @AllArgsConstructor
public class Post {

    @Id
    @Column(name = "post_id")
    @NotNull
    private int psid;

    @Autowired
    @ManyToOne
    @JoinColumn(name = "profile_id")
    @NotNull
    private Profile creator;

    @Column
    private String body;

    @Column(name = "image_url")
    private String imgURL;

    @Column(name = "date_posted")
    @NotNull
    private Timestamp datePosted;

    public Post() {
        psid = SecurityUtil.getId();
    }
}
