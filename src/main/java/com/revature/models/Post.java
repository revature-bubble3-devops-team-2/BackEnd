package com.revature.models;

import com.revature.utilites.SecurityUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Component
@Entity
@Data
public class Post {
    @Id
    private int psid;
    @ManyToOne
    private Profile creator;
    private String body;
    private String imgURL;
    private Timestamp datePosted;

    public Post() {
        psid = SecurityUtil.getId();
    }

    public Post(int psid, Profile creator, String body, String imgURL, Timestamp datePosted) {
        this.psid = psid;
        this.creator = creator;
        this.body = body;
        this.imgURL = imgURL;
        this.datePosted = datePosted;
    }
}
